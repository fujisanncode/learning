package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * 用户角色表
 *
 * @author hulei
 * @date 2020-03-18 20:52:20
 */
@Component
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

}
