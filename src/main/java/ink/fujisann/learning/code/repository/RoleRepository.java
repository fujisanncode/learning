package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.sys.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色表
 *
 * @author hulei
 * @date 2020/11/3
 */
@Component
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * 通过用户名查询角色信息
     *
     * @param userName 用户名
     * @return List<Role>
     */
    @Query(value = "select r.* from sys_user_t u, sys_user_role_t ur, sys_role_t r "
            + "where u.id = ur.userId and ur.roleId = r.id and u.name = ?1", nativeQuery = true)
    List<Role> findRolesByUserName(String userName);

    /**
     * 通过角色名查询角色对象(简单查询)
     *
     * @param roleName 角色名
     * @return ink.fujisann.learning.code.vo.sys.Role
     */
    Role findRoleByName(String roleName);

    /**
     * 通过用户名查找其为配置的角色
     *
     * @param userName 用户名
     * @return java.lang.Iterable<ink.fujisann.learning.code.vo.sys.Role>
     */
    @Query(value = "select r.* from sys_role_t r where not exists(select 1 from sys_user_t u,sys_user_role_t ur "
            + "where u.id = ur.userId and ur.roleId = r.id and u.name = ?1)", nativeQuery = true)
    Iterable<Role> findNonRoleByUserName(String userName);

    /**
     * 是否存在角色名称
     *
     * @param roleName 角色名称
     * @return true 存在
     */
    Boolean existsRoleByName(String roleName);
}
