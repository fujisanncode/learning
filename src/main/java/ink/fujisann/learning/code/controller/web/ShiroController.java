package ink.fujisann.learning.code.controller.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import ink.fujisann.learning.base.configure.shiro.MyRealm;
import ink.fujisann.learning.code.pojo.*;
import ink.fujisann.learning.code.repository.*;
import ink.fujisann.learning.code.service.ShiroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

/**
 * web页面权限管理
 *
 * @author hulei
 * @date 2020/11/2
 */
@RestController
@RequestMapping(value = ShiroController.SHIRO)
@Api(tags = "web页面权限管理")
@ApiSort(1)
@Slf4j
public class ShiroController {

    public static final String SHIRO = "/shiro-manage";

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
    @ApiOperation(value = "登入")
    @ApiOperationSupport(order = 1)
    public String login(@RequestBody User user) {
        return shiroService.login(user);
    }

    /**
     * 登出，登出即清除server中session，同时server会清除cookie返回客户端，此接口不拦截
     *
     * @param session 当前会话
     */
    @GetMapping("/logout")
    @ApiOperation(value = "登出", notes = "登出后清除session")
    public void logout(HttpSession session) {
        session.invalidate();
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
