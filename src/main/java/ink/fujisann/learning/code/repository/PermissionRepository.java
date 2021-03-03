package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限点表
 *
 * @author hulei
 * @date 2020-03-18 20:51:58
 */
@Component
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    /**
     * 根据用户名查询所有的接口权限点信息<br/>
     * nativeQuery=false,用实体及其字段写sql<br/>
     *
     * @param userName 用户名称
     * @return java.util.List<ink.fujisann.learning.code.vo.sys.Permission>
     * @author hulei
     * @date 2020-03-17 01:24:36
     */
    @Query(value = "select p.* from sys_user_t u, sys_user_role_t ur, sys_role_permission_t rp, sys_permission_t p "
            + "where u.id = ur.userId and ur.roleId = rp.roleId and rp.permissionId = p.id and u.name = ?1", nativeQuery = true)
    List<Permission> findPermissionsByUserName(String userName);

    /**
     * 通过角色名查询其所有的权限点
     *
     * @param roleName 角色名称
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.Permission>
     * @author hulei
     * @date 2020-03-17 22:43:44
     */
    @Query(value = "select p.* from sys_role_t r, sys_permission_t p, sys_role_permission_t rp where p.id = rp.permissionId "
            + "and r.id = rp.roleId and r.name = ?1", nativeQuery = true)
    Iterable<Permission> findPermissionByRoleName(String roleName);

    /**
     * 通过角色名查询其所有未配置的权限点
     *
     * @param roleName 角色名称
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.Permission>
     * @author hulei
     * @date 2020-03-17 23:51:14
     */
    @Query(value = "select p.* from sys_permission_t p where not exists(select 1 from sys_role_t r,sys_role_permission_t rp "
            + "where r.id = rp.roleId and rp.permissionId = p.id and r.name = ?1)", nativeQuery = true)
    Iterable<Permission> findNonPermissionByRoleName(String roleName);

    /**
     * 查询权限表所有权限点名称集合
     *
     * @return java.util.List<java.lang.String>
     */
    @Query (value = "select p.name from sys_permission_t p", nativeQuery = true)
    List<String> findAllName();

    /**
     * 通过权限点名称查询权限点对象
     *
     * @param name 权限点名称
     * @return ink.fujisann.learning.code.vo.sys.Permission
     */
    Permission findPermissionByName(String name);
    
}
