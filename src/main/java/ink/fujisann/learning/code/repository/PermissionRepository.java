package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.vo.sys.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 角色表
 * @author: hulei
 * @create: 2020-03-18 20:51:58
 */
@Component
public interface PermissionRepository extends CrudRepository<Permission, Integer> {

    /**
     * @description 根据用户名查询所有的接口权限点信息
     *              nativeQuery=false,用实体及其字段写sql
     * @param userName 用户名称
     * @return java.util.List<ink.fujisann.learning.code.vo.sys.Permission>
     * @author hulei
     * @date 2020-03-17 01:24:36
     */
    @Query (value = "select p.* from sys_user_t u, sys_user_role_t ur, sys_role_permission_t rp, sys_permission_t p "
        + "where u.id = ur.user_id and ur.role_id = rp.role_id and rp.permission_id = p.id and u.name = ?1", nativeQuery = true)
    List<Permission> findPermissionsByUserName(String userName);

    /**
     * @description 通过角色名查询其所有的权限点
     * @param RoleName 角色名称
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.Permission>
     * @author hulei
     * @date 2020-03-17 22:43:44
     */
    @Query (value = "select p.* from sys_role_t r, sys_permission_t p, sys_role_permission_t rp where p.id = rp.permission_id "
        + "and r.id = rp.role_id and r.name = ?1", nativeQuery = true)
    Iterable<Permission> findPermissionByRoleName(String RoleName);

    /**
     * @description 通过角色名查询其所有未配置的权限点
     * @param RoleName 角色名称
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.Permission>
     * @author hulei
     * @date 2020-03-17 23:51:14
     */
    @Query (value = "select p.* from sys_permission_t p where not exists(select 1 from sys_role_t r,sys_role_permission_t rp "
        + "where r.id = rp.role_id and rp.permission_id = p.id and r.name = ?1)", nativeQuery = true)
    Iterable<Permission> findNonPermissionByRoleName(String RoleName);

    /**
     * @description 查询权限表所有权限点名称集合
     * @return java.util.List<java.lang.String>
     * @author hulei
     * @date 2020-03-17 01:22:14
     */
    @Query (value = "select p.name from sys_permission_t p", nativeQuery = true)
    List<String> findAllName();

    /**
     * @description 通过权限点名称查询权限点对象
     * @param name 权限点名称
     * @return ink.fujisann.learning.code.vo.sys.Permission
     * @author hulei
     * @date 2020-03-17 22:46:45
     */
    Permission findPermissionByName(String name);

}
