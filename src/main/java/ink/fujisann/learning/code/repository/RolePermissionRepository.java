package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.sys.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 角色权限表
 *
 * @author hulei
 * @date 2020-03-18 20:52:37
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    /**
     * 按permissionId删除role-permission绑定
     *
     * @param permissionId 权限主键
     */
    void deleteRolePermissionByPermissionId(Integer permissionId);
}
