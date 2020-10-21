package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.vo.sys.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @description: 用户角色表
 * @author: hulei
 * @create: 2020-03-18 20:52:20
 */
@Component
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {

}
