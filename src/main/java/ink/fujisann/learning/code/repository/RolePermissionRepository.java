package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.sys.RolePermission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @description: 角色权限表
 * @author: hulei
 * @create: 2020-03-18 20:52:37
 */
@Component
public interface RolePermissionRepository extends CrudRepository<RolePermission, Integer> {

}
