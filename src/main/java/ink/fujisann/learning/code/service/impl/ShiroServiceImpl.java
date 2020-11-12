package ink.fujisann.learning.code.service.impl;

import com.alibaba.fastjson.JSONArray;
import ink.fujisann.learning.base.utils.common.LambdaUtil;
import ink.fujisann.learning.base.utils.common.ReadUtil;
import ink.fujisann.learning.base.utils.common.ScanAnnotation;
import ink.fujisann.learning.code.pojo.sys.*;
import ink.fujisann.learning.code.repository.*;
import ink.fujisann.learning.code.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * shiro权限管理相关接口
 *
 * @author hulei
 * @version 2020/11/2
 */
@Service
@Slf4j
public class ShiroServiceImpl implements ShiroService {
    private PermissionRepository permissionRepository;

    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void addUser(User user) {
        if (!userRepository.existsUserByName(user.getName())) {
            userRepository.save(user);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addExistPermission() {
        // 扫描最新代码获取当前权限点集合A
        List<String> allPermission = ScanAnnotation.buildPermissionListByPackage("ink.fujisann.learning.code.controller.web");
        log.info("scanned ======> {}", JSONArray.toJSONString(allPermission));
        // 查询数据库中当前保存的权限点集合B
        List<Permission> existPermission = IterableUtils.toList(permissionRepository.findAll());

        // 如果A中权限点在B中存在，则排除掉不需要重复写入
        ArrayList<String> copy = new ArrayList<>(allPermission);
        copy.removeIf(a -> existPermission.stream().anyMatch(b -> b.getName().equals(a)));
        savePermissionBatch(copy);

        // 如果B中权限点在A中不存在，则将这些废弃的权限点从数据库中删除掉
        existPermission.removeIf(b -> allPermission.contains(b.getName()));
        // 先删除角色权限绑定关系
        existPermission.forEach(permission -> {
            rolePermissionRepository.deleteRolePermissionByPermissionId(permission.getId());
        });
        // 然后删除权限，delete参数为对象时，对象不能是new的（未持久化），必须是查询出来的
        permissionRepository.deleteAll(existPermission);
    }

    /**
     * 批量写入数据库的条数
     */
    public static final int BATCH_COUNT = 100;

    /**
     * 权限集合批量写表
     *
     * @param allInsertPermissionName 需要写表的权限集合
     */
    private void savePermissionBatch(List<String> allInsertPermissionName) {
        List<Permission> permissionList = new ArrayList<>();
        allInsertPermissionName.forEach(LambdaUtil.withIndex((permissionName, index) -> {
            if (index == BATCH_COUNT) {
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

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private UserRoleRepository userRoleRepository;

    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Autowired
    public void setRolePermissionRepository(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public void addRole(Role role) {
        if (!roleRepository.existsRoleByName(role.getName())) {
            roleRepository.save(role);
        }
    }

    @Override
    public void bindUserRole(UserRole userRole) {
        // 根据用户名查询用户表主键id；写入角色表后，根据角色名称，查询角色id；组装查询到的用户id和角色id
        User userOut = userRepository.findUserByName(userRole.getUser().getName());
        Role roleOut = roleRepository.findRoleByName(userRole.getRole().getName());
        userRole.setUser(userOut);
        userRole.setRole(roleOut);

        // 写用户-角色关联表
        userRoleRepository.save(userRole);
    }

    @Override
    public void addPermission(Permission permission) {
        permissionRepository.save(permission);
    }

    @Override
    public void bindRolePermission(RolePermission rolePermission) {
        // 根据角色名查询角色表主键id；写入接口权限点表后，根据接口权限点名称，查询接口权限点id；组装查询到的角色id和接口权限点id
        Role roleOut = roleRepository.findRoleByName(rolePermission.getRole().getName());
        Permission permissionOut = permissionRepository.findPermissionByName(rolePermission.getPermission().getName());
        rolePermission.setRole(roleOut);
        rolePermission.setPermission(permissionOut);

        // 写角色-接口权限点关联表
        rolePermissionRepository.save(rolePermission);
    }

    @Override
    public void bindUserRoleBatch(Iterable<UserRole> userRole) {
        userRoleRepository.saveAll(userRole);
    }

    @Override
    public void bindRolePermissionBatch(Iterable<RolePermission> rolePermission) {
        rolePermissionRepository.saveAll(rolePermission);
    }

    /**
     * 内置用户名
     */
    @Value("${fujisann.login.name}")
    private String defaultUser;

    /**
     * 内置用户密码
     */
    @Value("${fujisann.login.password}")
    private String defaultUserPwd;

    /**
     * 内置角色
     */
    @Value("${fujisann.login.role}")
    private String defaultRole;

    @Override
    public void addDefaultUser() {
        // 如果用户不存在，则保存
        if (!userRepository.existsUserByName(defaultUser)) {
            User insertUser = new User();
            insertUser.setName(defaultUser);
            insertUser.setPassword(defaultUserPwd);
            Role insertRole = new Role();
            insertRole.setName(defaultRole);
            UserRole userRole = new UserRole();
            userRole.setUser(insertUser);
            userRole.setRole(insertRole);
            // 保存userRole，则user和role级联保存
            userRoleRepository.save(userRole);
        } else {
            // 如果用户存在，但密码和默认密码不匹配，则更新为默认密码
            User curUser = userRepository.findUserByName(defaultUser);
            if (!defaultUserPwd.equals(curUser.getPassword())) {
                curUser.setPassword(defaultUserPwd);
                userRepository.save(curUser);
            }
        }
    }

    /**
     * shiro的session超时时间
     */
    private static final long SHIRO_TIME_OUT = 30 * 60 * 1000;

    @Override
    public String login(User user) {
        try {
            Subject subject = SecurityUtils.getSubject();
            // 设置session超时时间
            subject.getSession().setTimeout(SHIRO_TIME_OUT);
            // 将请求中用户名、密码传入shiro中进行验证并生成sessionId
            UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
            subject.login(token);
            // 登录成功，则响应头中通过set-cookie字段返回生成的sessionId
            return findRouterByUserName("xxx");
        } catch (AuthenticationException e) {
            throw new UnauthenticatedException();
        } catch (AuthorizationException e) {
            throw new UnauthorizedException();
        } catch (Exception e) {
            throw new RuntimeException("登录接口异常");
        }
    }

    private String findRouterByUserName(String userName) {
        return ReadUtil.readJson("findRouterByUserId");
    }

    @Override
    public void defaultRoleBindPermission() {
        // 找到已经配置的权限点
        List<RolePermission> exist = rolePermissionRepository.findAll();

        // 找到内置角色、所有的权限点
        Role definedRole = roleRepository.findRoleByName(this.defaultRole);
        ArrayList<RolePermission> insertBatch = new ArrayList<>();
        permissionRepository.findAll().forEach(permission -> {
            // 在exist中不存在的，需要添加的权限绑定
            boolean noneExistFlag = exist.stream().noneMatch(
                    rolePermission -> rolePermission.getPermission().getId().equals(permission.getId())
            );
            // 将失效的权限绑定删除，todo
            if (noneExistFlag) {
                RolePermission insert = new RolePermission();
                Role role = new Role();
                role.setId(definedRole.getId());
                insert.setRole(role);
                insert.setPermission(permission);
                insertBatch.add(insert);
            }
        });
        rolePermissionRepository.saveAll(insertBatch);

    }
}
