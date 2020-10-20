package ink.fujisann.learning.configure.shiro;

import ink.fujisann.learning.exception.BusinessException.ExceptionBuilder;
import ink.fujisann.learning.repository.PermissionRepository;
import ink.fujisann.learning.repository.RolePermissionRepository;
import ink.fujisann.learning.repository.RoleRepository;
import ink.fujisann.learning.repository.UserRepository;
import ink.fujisann.learning.repository.UserRoleRepository;
import ink.fujisann.learning.utils.common.LambdaUtil;
import ink.fujisann.learning.utils.common.ScanAnnotation;
import ink.fujisann.learning.vo.sys.Permission;
import ink.fujisann.learning.vo.sys.Role;
import ink.fujisann.learning.vo.sys.RolePermission;
import ink.fujisann.learning.vo.sys.User;
import ink.fujisann.learning.vo.sys.UserRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = ShiroManage.shiro)
@Api (value = "001 shiro manage", tags = "001-shiro权限管理服务")
@Slf4j
public class ShiroManage {

    public static final String shiro = "/shiro-manage";
    // shiro的session超时时间
    private static final long shiroTimeOut = 5 * 60 * 1000;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private RoleRepository roleRepository;
    private RolePermissionRepository rolePermissionRepository;
    private PermissionRepository permissionRepository;
    private EntityManager entityManager;

    @Autowired
    public ShiroManage(UserRepository userRepository) {
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
     * @description 不拦截的请求，登录验证后返回sessionId，失败后返回非200的状态码
     * @param user 登录用户
     * @return java.io.Serializable
     * @author hulei
     * @date 2020-03-11 21:32:31
     */
    @PostMapping ("/login")
    @ApiOperation (value = "login", notes = "验证登录用户名和密码")
    public Serializable login(@RequestBody User user) {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.getSession().setTimeout(shiroTimeOut);
            UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
            subject.login(token);
            // 无论是否登录成功， server都会通过cookie返回sessionId给客户端
            // 客户端再次和server交互，通过cookie将sessionId交给server判断当前会话客户端是否已经登录
            return subject.getSession().getId();
        } catch (AuthenticationException e) {
            throw new ExceptionBuilder().setStatus(HttpStatus.UNAUTHORIZED.value())
                .setCode("401").setMsg("账号/密码错误").build();
        } catch (AuthorizationException e) {
            throw new ExceptionBuilder().setStatus(HttpStatus.FORBIDDEN.value())
                .setCode("403").setMsg("权限错误").build();
        } catch (Exception e) {
            throw new ExceptionBuilder().setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setCode("500").setMsg("服务器错误").build();
        }
    }

    /**
     * @description 登出，登出即清除server中session，同时server会清除cookie返回客户端，此接口不拦截
     * @param session 当前会话
     * @author hulei
     * @date 2020-03-14 17:11:42
     */
    @GetMapping ("/logout")
    @ApiOperation (value = "logout", notes = "登出清除session")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    /**
     * @description 调用未登录会跳转到这里，统一返回401，然后让异常处理器解析异常返回
     *               不指定get或者post，保证不同的http请求方式都能跳转
     * @author hulei
     * @date 2020-03-11 22:22:23
     */
    @RequestMapping ("/noLogin")
    @ApiOperation (value = "noLogin", notes = "未登录统一返回")
    public void noLogin() {
        throw new ExceptionBuilder().setStatus(HttpStatus.UNAUTHORIZED.value())
            .setCode("401").setMsg("跳转到未登录接口").build();
    }

    // 调用接口没有权限时，统一用此接口按照401的状态码返回页面，否则没有权限的接口会按照shiro默认返回
    @RequestMapping ("/unAuth")// 不指定get或者post，保证不同的http请求方式都能跳转
    @ApiOperation (value = "unAuth", notes = "未授权统一返回")
    public void unAuth() {
        throw new ExceptionBuilder().setStatus(HttpStatus.FORBIDDEN.value())
            .setCode("403").setMsg("跳转到未授权接口").build();
    }

    /**
     * @description 测试前后台cookie交互
     * @return java.lang.String
     * @author hulei
     * @date 2020-03-11 21:38:15
     */
    @GetMapping ("/index")
    @ApiOperation (value = "login page", notes = "登录页面")
    @RequiresPermissions ("/shiro-manage/index")
    public String index() {
        return String.valueOf(new Date().getTime());
    }

    // 清空用户、角色、接口权限表
    @GetMapping ("/trunkTable")
    @RequiresPermissions ("/shiro-manage/trunkTable")
    @ApiOperation (value = "trunkTable", notes = "删除5张表全部数据")
    @Transactional
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

    /**
     * @description 新增用户接口
     * @param user 用户
     * @author hulei
     * @date 2020-03-15 21:30:08
     */
    @PostMapping ("/addUserSingle")
    @RequiresPermissions ("/shiro-manage/addUserSingle")
    @ApiOperation (value = "addUserSingle", notes = "用户信息写入用户表")
    public void addUserSingle(@RequestBody User user) {
        // 写用户表
        userRepository.save(user);
    }

    /**
     * @description 创建新的角色
     * @param role 角色
     * @author hulei
     * @date 2020-03-18 20:28:39
     */
    @PostMapping ("/addRoleSingle")
    @RequiresPermissions ("/shiro-manage/addRoleSingle")
    @ApiOperation (value = "addRoleSingle", notes = "创建新的角色")
    public void addRoleSingle(@RequestBody Role role) {
        // 写角色表
        roleRepository.save(role);
    }

    /**
     * @description 扫描全部权限注解，写入数据库
     * @author hulei
     * @date 2020-03-17 01:05:19
     */
    @GetMapping ("/addExistPermission")
    @RequiresPermissions ("/shiro-manage/addExistPermission")
    @ApiOperation (value = "addExistPermission", notes = "代码中权限点全部写入数据库")
    @Transactional
    public void addExistPermission() {
        List<String> allPermission = ScanAnnotation.getValueByClass(ShiroManage.class);
        List<String> hasExistPermission = permissionRepository.findAllName();
        // 过滤满足验证条件的权限点
        allPermission.removeIf(hasExistPermission::contains);
        savePermissionBatch(allPermission);
    }

    /**
     * @description 权限集合批量写表
     * @param allInsertPermissionName 需要写表的权限集合
     * @author hulei
     * @date 2020-03-17 01:06:04
     */
    private void savePermissionBatch(List<String> allInsertPermissionName) {
        List<Permission> permissionList = new ArrayList<>();
        allInsertPermissionName.forEach(LambdaUtil.withIndex((permissionName, index) -> {
            if (index == 100) {
                permissionRepository.saveAll(permissionList);
                permissionList.clear();
            }
            Permission permissionTemp = new Permission();
            permissionTemp.setName(permissionName);
            permissionList.add(permissionTemp);
        }));
        if (!permissionList.isEmpty()) {
            permissionRepository.saveAll(permissionList);
        }
    }

    /**
     * @description 给特定用户增加角色
     * @param userRole 用户角色关联对象
     * @author hulei
     * @date 2020-03-15 21:30:48
     */
    @PostMapping ("/addRole")
    @RequiresPermissions ("/shiro-manage/addRole")
    @ApiOperation (value = "addRole", notes = "按照用户名给用户添加角色")
    @Transactional// 事务隔离，事务传播，回滚；全部使用默认值
    @Deprecated
    public void addRole(@RequestBody UserRole userRole) {
        // 写角色表
        roleRepository.save(userRole.getRole());

        // 根据用户名查询用户表主键id；写入角色表后，根据角色名称，查询角色id；组装查询到的用户id和角色id
        User userOut = userRepository.findUserByName(userRole.getUser().getName());
        Role roleOut = roleRepository.findRoleByName(userRole.getRole().getName());
        userRole.setUser(userOut);
        userRole.setRole(roleOut);

        // 写用户-角色关联表
        userRoleRepository.save(userRole);
    }

    /**
     * @description 给特定角色赋接口权限
     * @param rolePermission 角色权限关联对象
     * @author hulei
     * @date 2020-03-15 21:31:29
     */
    @PostMapping ("/addPermission")
    @RequiresPermissions ("/shiro-manage/addPermission")
    @ApiOperation (value = "addPermission", notes = "给指定角色添加接口权限")
    @Transactional
    @Deprecated
    public void addPermission(@RequestBody RolePermission rolePermission) {
        // 写接口权限点
        permissionRepository.save(rolePermission.getPermission());

        // 根据角色名查询角色表主键id；写入接口权限点表后，根据接口权限点名称，查询接口权限点id；组装查询到的角色id和接口权限点id
        Role roleOut = roleRepository.findRoleByName(rolePermission.getRole().getName());
        Permission permissionOut = permissionRepository.findPermissionByName(rolePermission.getPermission().getName());
        rolePermission.setRole(roleOut);
        rolePermission.setPermission(permissionOut);

        // 写角色-接口权限点关联表
        rolePermissionRepository.save(rolePermission);
    }

    /**
     * @description 给用户批量指定职责
     * @param UserRole 用户职责对象
     * @return java.lang.String
     * @author hulei
     * @date 2020-03-18 20:31:34
     */
    @PostMapping ("/addUserRoleBatch")
    @RequiresPermissions ("/shiro-manage/addUserRoleBatch")
    @ApiOperation (value = "addUserRoleBatch", notes = "批量写入用户角色表")
    public String addUserRoleBatch(@RequestBody Iterable<UserRole> UserRole) {
        userRoleRepository.saveAll(UserRole);
        return "success";
    }

    /**
     * @description 批量写入角色权限表(给角色添加权限)
     * @param rolePermission 角色权限关联对象
     * @author hulei
     * @date 2020-03-17 23:27:46
     */
    @PostMapping ("/addRolePermissionBatch")
    @RequiresPermissions ("/shiro-manage/addRolePermissionBatch")
    @ApiOperation (value = "addRolePermissionBatch", notes = "批量写入角色权限表")
    public String addRolePermissionBatch(@RequestBody Iterable<RolePermission> rolePermission) {
        rolePermissionRepository.saveAll(rolePermission);
        return "success";
    }

    /**
     * @description 查询所有系统用户
     * @return java.lang.Iterable<com.example.sqllearning.vo.sys.User>
     * @author hulei
     * @date 2020-03-15 21:34:24
     */
    @GetMapping ("/findAllUser")
    @RequiresPermissions ("/shiro-manage/findAllUser")
    @ApiOperation (value = "findAllUser", notes = "查询所有系统用户")
    public Iterable<User> findAllUser() {
        return userRepository.findAll();
    }

    /**
     * @description 查找所有的系统角色
     * @return java.lang.Iterable<com.example.sqllearning.vo.sys.Role>
     * @author hulei
     * @date 2020-03-15 21:34:48
     */
    @GetMapping ("/findAllRole")
    @RequiresPermissions ("/shiro-manage/findAllRole")
    @ApiOperation (value = "findAllRole", notes = "查找所有的系统角色")
    public Iterable<Role> findAllRole() {
        return roleRepository.findAll();
    }

    /**
     * @description 查询所有接口权限点
     * @return java.lang.Iterable<com.example.sqllearning.vo.sys.Permission>
     * @author hulei
     * @date 2020-03-15 21:35:09
     */
    @GetMapping ("/findAllPermission")
    @RequiresPermissions ("/shiro-manage/findAllPermission")
    @ApiOperation (value = "findAllPermission", notes = "查找所有接口权限点")
    public Iterable<Permission> findAllPermission() {
        return permissionRepository.findAll();
    }

    /**
     * @description 通过用户名查询角色集合
     * @param userName 用户名
     * @return java.lang.Iterable<com.example.sqllearning.vo.sys.Role>
     * @author hulei
     * @date 2020-03-17 21:48:49
     */
    @GetMapping ("/findRoleByUser")
    @RequiresPermissions ("/shiro-manage/findRoleByUser")
    @ApiOperation (value = "findRoleByUser", notes = "通过用户名查找角色集合")
    public Iterable<Role> findRoleByUser(@RequestParam (value = "userName") String userName) {
        return roleRepository.findRolesByUserName(userName);
    }

    /**
     * @description 通过角色名称查找所有的权限点
     * @param roleName 角色名称
     * @return java.lang.Iterable<com.example.sqllearning.vo.sys.Permission>
     * @author hulei
     * @date 2020-03-17 22:48:52
     */
    @GetMapping ("/findPermissionByRole")
    @RequiresPermissions ("/shiro-manage/findPermissionByRole")
    @ApiOperation (value = "findPermissionByRole", notes = "通过角色名查找权限点集合")
    public Iterable<Permission> findPermissionByRole(@RequestParam (value = "roleName") String roleName) {
        return permissionRepository.findPermissionByRoleName(roleName);
    }

    /**
     * @description 通过角色名查找未配置的权限点集合
     * @param roleName 角色名称
     * @return java.lang.Iterable<com.example.sqllearning.vo.sys.Permission>
     * @author hulei
     * @date 2020-03-17 23:50:19
     */
    @GetMapping ("/findNonPermissionByRole")
    @RequiresPermissions ("/shiro-manage/findNonPermissionByRole")
    @ApiOperation (value = "findNonPermissionByRole", notes = "通过角色名查找未配置的权限点集合")
    public Iterable<Permission> findNonPermissionByRole(@RequestParam (value = "roleName") String roleName) {
        return permissionRepository.findNonPermissionByRoleName(roleName);
    }

    /**
     * @description 查找用户没有配置的角色
     * @param userName 用户名
     * @return java.lang.Iterable<com.example.sqllearning.vo.sys.Role>
     * @author hulei
     * @date 2020-03-18 20:53:40
     */
    @GetMapping ("/findNonRoleByUser")
    @RequiresPermissions ("/shiro-manage/findNonRoleByUser")
    @ApiOperation (value = "findNonRoleByUser", notes = "通过用户查找未配置的角色")
    public Iterable<Role> findNonRoleByUser(@RequestParam (value = "userName") String userName) {
        return roleRepository.findNonRoleByUserName(userName);
    }
}
