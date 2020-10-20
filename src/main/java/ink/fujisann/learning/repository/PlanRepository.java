package ink.fujisann.learning.repository;

import ink.fujisann.learning.vo.plan.Plan;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepository extends CrudRepository<Plan, String> {

}
