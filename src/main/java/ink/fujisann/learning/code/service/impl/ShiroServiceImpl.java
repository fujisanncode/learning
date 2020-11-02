package ink.fujisann.learning.code.service.impl;

import ink.fujisann.learning.base.utils.common.LambdaUtil;
import ink.fujisann.learning.base.utils.common.ScanAnnotation;
import ink.fujisann.learning.code.controller.web.ShiroController;
import ink.fujisann.learning.code.pojo.sys.*;
import ink.fujisann.learning.code.repository.*;
import ink.fujisann.learning.code.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<String> allPermission = ScanAnnotation.getValueByClass(ShiroController.class);
        // 查询数据库中当前保存的权限点集合B
        List<Permission> existPermission = IterableUtils.toList(permissionRepository.findAll());

        // 如果A中权限点在B中存在，则排除掉不需要重复写入
        allPermission.removeIf(a -> existPermission.stream().anyMatch(b -> b.getName().equals(a)));
        savePermissionBatch(allPermission);

        // 如果B中权限点在A中不存在，则将这些废弃的权限点从数据库中删除掉
        existPermission.removeIf(b -> allPermission.contains(b.getName()));
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
    private static final String DEFAULT_USER = "fujisann";

    /**
     * 内置用户密码
     */
    private static final String DEFAULT_USER_PWD = "fujisannTest";

    /**
     * 内置角色
     */
    private static final String DEFAULT_ROLE = "admin";

    @Override
    public void addDefaultUser() {
        if (!userRepository.existsUserByName(DEFAULT_USER)) {
            User insertUser = new User();
            insertUser.setName(DEFAULT_USER);
            insertUser.setPassword(DEFAULT_USER_PWD);
            Role insertRole = new Role();
            insertRole.setName(DEFAULT_ROLE);
            UserRole userRole = new UserRole();
            userRole.setUser(insertUser);
            userRole.setRole(insertRole);
            // 保存userRole，则user和role级联保存
            userRoleRepository.save(userRole);
        }
    }
}
