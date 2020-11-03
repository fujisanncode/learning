package ink.fujisann.learning.code.controller.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import ink.fujisann.learning.base.configure.shiro.MyRealm;
import ink.fujisann.learning.base.exception.BusinessException;
import ink.fujisann.learning.code.pojo.sys.*;
import ink.fujisann.learning.code.repository.*;
import ink.fujisann.learning.code.service.ShiroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * web页面权限管理
 *
 * @author hulei
 * @date 2020/11/2
 */
@RestController
@RequestMapping(value = ShiroController.SHIRO)
@Api(tags = "web页面权限管理")
@ApiSupport(order = 1)
@Slf4j
public class ShiroController {

    public static final String SHIRO = "/shiro-manage";

    /**
     * shiro的session超时时间
     */
    private static final long SHIRO_TIME_OUT = 30 * 60 * 1000;

    private final UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private RoleRepository roleRepository;
    private RolePermissionRepository rolePermissionRepository;
    private PermissionRepository permissionRepository;
    private EntityManager entityManager;

    @Autowired
    public ShiroController(UserRepository userRepository) {
        // 强制依赖注入,或者用set方法非强制注入
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setRolePermissionRepository(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 此接口需要设置为shiro不拦截的请求<br/>
     * 登录验证后返回sessionId<br/>
     * 失败后返回非200的状态码<br/>
     * 验证登录用户名和密码见 {@linkplain MyRealm}<br/>
     *
     * @param user 登录用户
     * @return 为指定用户生成当前会话的唯一标志服：sessionId
     */
    @PostMapping("/login")
    @ApiOperation(value = "login", notes = "验证登录用户名和密码")
    @ApiOperationSupport(order = 1)
    public Serializable login(@RequestBody User user) {
        try {
            Subject subject = SecurityUtils.getSubject();
            // 设置session超时时间
            subject.getSession().setTimeout(SHIRO_TIME_OUT);
            // 将请求中用户名、密码传入shiro中进行验证并生成sessionId
            UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
            subject.login(token);
            // 登录成功，则响应头中通过set-cookie字段返回生成的sessionId
            return "success";
        } catch (AuthenticationException e) {
            throw new UnauthenticatedException();
        } catch (AuthorizationException e) {
            throw new UnauthorizedException();
        } catch (Exception e) {
            throw new RuntimeException("登录接口异常");
        }
    }

    /**
     * 登出，登出即清除server中session，同时server会清除cookie返回客户端，此接口不拦截
     *
     * @param session 当前会话
     */
    @GetMapping("/logout")
    @ApiOperation(value = "logout", notes = "登出清除session")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    /**
     * 未登录调用接口会跳转到此处<br/>
     * 不指定get或者post，保证不同的http请求方式都能跳转<br/>
     */
    @RequestMapping("/noLogin")
    @ApiOperation(value = "noLogin", notes = "未登录统一返回")
    public void noLogin() {
        throw new AuthenticationException("通过/noLogin接口返回");
    }

    /**
     * 调用接口没有权限时，统一用此接口按照401的状态码返回页面，否则没有权限的接口会按照shiro默认返回<br/>
     * 不指定get或者post，保证不同的http请求方式都能跳转
     */
    @RequestMapping("/unAuth")
    @ApiOperation(value = "unAuth", notes = "未授权统一返回")
    public void unAuth() {
        throw new BusinessException.Builder()
                .code("403").msg("跳转到未授权接口").build();
    }

    /**
     * 测试前后台cookie交互
     *
     * @return java.lang.String
     */
    @GetMapping("/index")
    @ApiOperation(value = "login page", notes = "登录页面")
    @RequiresPermissions("/shiro-manage/index")
    public String index() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 清空用户、角色、接口权限表
     */
    @GetMapping("/trunkTable")
    @RequiresPermissions("/shiro-manage/trunkTable")
    @ApiOperation(value = "trunkTable", notes = "删除5张表全部数据")
    @Transactional(rollbackFor = Exception.class)
    public void trunkTable() {
        // 先删除关联表，然后删除单表
        rolePermissionRepository.deleteAll();
        userRoleRepository.deleteAll();
        permissionRepository.deleteAll();
        roleRepository.deleteAll();
        userRepository.deleteAll();
        // 保存上下文中没有保存的是实体到数据库，否则insert会报重复的错误
        entityManager.flush();
        // 写入管理员用户
        User user = new User();
        user.setName("hulei");
        userRepository.save(user);
    }

    private ShiroService shiroService;

    @Autowired
    public void setShiroService(ShiroService shiroService) {
        this.shiroService = shiroService;
    }

    /**
     * 创建新的用户
     *
     * @param user 用户
     */
    @PostMapping("/addUserSingle")
    @ApiOperation(value = "创建新的用户")
    @RequiresPermissions("/shiro-manage/addUserSingle")
    public void addUserSingle(@RequestBody User user) {
        shiroService.addUser(user);
    }

    /**
     * 创建新的角色
     *
     * @param role 角色
     */
    @PostMapping("/addRoleSingle")
    @ApiOperation(value = "addRoleSingle", notes = "创建新的角色")
    @RequiresPermissions("/shiro-manage/addRoleSingle")
    public void addRoleSingle(@RequestBody Role role) {
        shiroService.addRole(role);
    }

    /**
     * 扫描全部权限注解，写入数据库
     */
    @GetMapping("/addExistPermission")
    @ApiOperation(value = "扫描shiro的权限点注解并写入数据库")
    @RequiresPermissions("/shiro-manage/addExistPermission")
    public void addExistPermission() {
        shiroService.addExistPermission();
    }

    /**
     * 给用户批量指定职责
     *
     * @param userRole 用户职责对象
     */
    @PostMapping("/addUserRoleBatch")
    @ApiOperation(value = "角色批量绑定到用户")
    @RequiresPermissions("/shiro-manage/addUserRoleBatch")
    public void addUserRoleBatch(@RequestBody Iterable<UserRole> userRole) {
        shiroService.bindUserRoleBatch(userRole);
    }

    /**
     * 批量写入角色权限表(给角色添加权限)
     *
     * @param rolePermission 角色权限关联对象
     */
    @PostMapping("/addRolePermissionBatch")
    @ApiOperation(value = "权限点批量绑定到角色")
    @RequiresPermissions("/shiro-manage/addRolePermissionBatch")
    public void addRolePermissionBatch(@RequestBody Iterable<RolePermission> rolePermission) {
        shiroService.bindRolePermissionBatch(rolePermission);
    }

    /**
     * 查询所有系统用户
     *
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.User>
     */
    @GetMapping("/findAllUser")
    @RequiresPermissions("/shiro-manage/findAllUser")
    @ApiOperation(value = "findAllUser", notes = "查询所有系统用户")
    public Iterable<User> findAllUser() {
        return userRepository.findAll();
    }

    /**
     * 查找所有的系统角色
     *
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.Role>
     */
    @GetMapping("/findAllRole")
    @RequiresPermissions("/shiro-manage/findAllRole")
    @ApiOperation(value = "findAllRole", notes = "查找所有的系统角色")
    public Iterable<Role> findAllRole() {
        return roleRepository.findAll();
    }

    /**
     * 查询所有接口权限点
     *
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.Permission>
     */
    @GetMapping("/findAllPermission")
    @RequiresPermissions("/shiro-manage/findAllPermission")
    @ApiOperation(value = "findAllPermission", notes = "查找所有接口权限点")
    public Iterable<Permission> findAllPermission() {
        return permissionRepository.findAll();
    }

    /**
     * 通过用户名查询角色集合
     *
     * @param userName 用户名
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.Role>
     */
    @GetMapping("/findRoleByUser")
    @RequiresPermissions("/shiro-manage/findRoleByUser")
    @ApiOperation(value = "findRoleByUser", notes = "通过用户名查找角色集合")
    public Iterable<Role> findRoleByUser(@RequestParam(value = "userName") String userName) {
        return roleRepository.findRolesByUserName(userName);
    }

    /**
     * 通过角色名称查找所有的权限点
     *
     * @param roleName 角色名称
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.Permission>
     */
    @GetMapping("/findPermissionByRole")
    @RequiresPermissions("/shiro-manage/findPermissionByRole")
    @ApiOperation(value = "findPermissionByRole", notes = "通过角色名查找权限点集合")
    public Iterable<Permission> findPermissionByRole(@RequestParam(value = "roleName") String roleName) {
        return permissionRepository.findPermissionByRoleName(roleName);
    }

    /**
     * 通过角色名查找未配置的权限点集合
     *
     * @param roleName 角色名称
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.Permission>
     */
    @GetMapping("/findNonPermissionByRole")
    @RequiresPermissions("/shiro-manage/findNonPermissionByRole")
    @ApiOperation(value = "findNonPermissionByRole", notes = "通过角色名查找未配置的权限点集合")
    public Iterable<Permission> findNonPermissionByRole(@RequestParam(value = "roleName") String roleName) {
        return permissionRepository.findNonPermissionByRoleName(roleName);
    }

    /**
     * 查找用户没有配置的角色
     *
     * @param userName 用户名
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.Role>
     */
    @GetMapping("/findNonRoleByUser")
    @RequiresPermissions("/shiro-manage/findNonRoleByUser")
    @ApiOperation(value = "findNonRoleByUser", notes = "通过用户查找未配置的角色")
    public Iterable<Role> findNonRoleByUser(@RequestParam(value = "userName") String userName) {
        return roleRepository.findNonRoleByUserName(userName);
    }
}
