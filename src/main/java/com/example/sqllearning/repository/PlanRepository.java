package com.example.sqllearning.repository;

import com.example.sqllearning.vo.plan.Plan;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepository extends CrudRepository<Plan, String> {

}
