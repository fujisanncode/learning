package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 计划
 *
 * @author hulei
 * @date 2020/11/11
 */
public interface PlanRepository extends JpaRepository<Plan, String> {

}
