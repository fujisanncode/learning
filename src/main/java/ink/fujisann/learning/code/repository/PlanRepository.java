package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * 计划
 *
 * @author hulei
 * @date 2020/11/11
 */
public interface PlanRepository extends JpaRepository<Plan, String> {

}
