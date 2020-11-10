package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.sys.RolePermission;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 角色权限表
 *
 * @author hulei
 * @date 2020-03-18 20:52:37
 */
@Repository
public interface RolePermissionRepository extends CrudRepository<RolePermission, Integer> {

    /**
     * 角色绑定权限
     *
     * @param rolePermission 角色权限
     * @return 行数
     */
    @Modifying
    @Query(value = "insert into RolePermission(roleId, permissionId) " +
            "values(:#{#rolePermission.role.id}, :#{#rolePermission.permission.id})", nativeQuery = false)
    Integer bind(RolePermission rolePermission);
}
