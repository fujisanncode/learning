package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.vo.sys.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 角色表
 * @author: hulei
 * @create: 2020-03-18 20:50:08
 */
@Component
public interface RoleRepository extends CrudRepository<Role, Integer> {

    /**
     * @description 通过用户名查询角色信息
     * @param userName 用户名
     * @return List<Role>
     * @author hulei
     * @date 2020-03-18 20:40:21
     */
    @Query (value = "select r.* from sys_user_t u, sys_user_role_t ur, sys_role_t r "
        + "where u.id = ur.user_id and ur.role_id = r.id and u.name = ?1", nativeQuery = true)
    List<Role> findRolesByUserName(String userName);

    /**
     * @description 通过角色名查询角色对象(简单查询)
     * @param roleName 角色名
     * @return ink.fujisann.learning.code.vo.sys.Role
     * @author hulei
     * @date 2020-03-18 20:39:37
     */
    Role findRoleByName(String roleName);

    /**
     * @description 通过用户名查找其为配置的角色
     * @param UserName 用户名
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.Role>
     * @author hulei
     * @date 2020-03-18 20:38:58
     */
    @Query (value = "select r.* from sys_role_t r where not exists(select 1 from sys_user_t u,sys_user_role_t ur "
        + "where u.id = ur.user_id and ur.role_id = r.id and u.name = ?1)", nativeQuery = true)
    Iterable<Role> findNonRoleByUserName(String UserName);
}
