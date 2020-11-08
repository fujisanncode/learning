package ink.fujisann.learning.code.service;

import ink.fujisann.learning.code.pojo.sys.*;

/**
 * shiro权限管理相关接口
 *
 * @author hulei
 * @version 2020/11/2
 */
public interface ShiroService {

    /**
     * 新增用户
     *
     * @param user 用户
     */
    void addUser(User user);

    /**
     * 为添加到数据库的接口权限点写入数据库
     */
    void addExistPermission();

    /**
     * 新增角色
     *
     * @param role 角色
     */
    void addRole(Role role);

    /**
     * 角色绑定到用户
     *
     * @param userRole 用户角色
     */
    void bindUserRole(UserRole userRole);

    /**
     * 角色批量绑定到用户
     *
     * @param userRole 用户角色集合
     */
    void bindUserRoleBatch(Iterable<UserRole> userRole);

    /**
     * 新增权限点
     *
     * @param permission 权限点
     */
    void addPermission(Permission permission);

    /**
     * 权限点绑定到角色
     *
     * @param rolePermission 角色权限
     */
    void bindRolePermission(RolePermission rolePermission);

    /**
     * 权限点批量绑定到角色
     *
     * @param rolePermission 角色权限
     */
    void bindRolePermissionBatch(Iterable<RolePermission> rolePermission);

    /**
     * 新增默认用户、角色
     */
    void addDefaultUser();

    /**
     * 登录接口
     *
     * @param user 用户信息
     * @return 登录成功则查询用户的路由和菜单
     */
    String login(User user);

}
