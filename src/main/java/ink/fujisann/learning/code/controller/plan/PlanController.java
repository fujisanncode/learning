package ink.fujisann.learning.code.controller.plan;

import ink.fujisann.learning.code.repository.PlanRepository;
import ink.fujisann.learning.code.vo.plan.Plan;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 制定计划
 * @author: hulei
 * @create: 2020-05-27 19:44:53
 */
@RestController
@RequestMapping("/plan")
@Api(
    value = "PlanController",
    tags = {"002-制定计划"})
public class PlanController {

  @Autowired private PlanRepository planRepository;

  @PostMapping("/new-plan")
  @ApiOperation(value = "newPlan", notes = "新增一条计划")
  public Plan newPlan(@RequestBody Plan plan) {
    return planRepository.save(plan);
  }

  @GetMapping("/plan-list")
  @ApiOperation(value = "findAllPlans", notes = "查询所有的计划")
  public Iterable<Plan> findAllPlans() {
    return planRepository.findAll();
  }

  @ApiOperation(value = "deletePlanById", notes = "根据主键id删除计划")
  @ApiImplicitParam(name = "planId", value = "主键id", dataType = "string", paramType = "path")
  @DeleteMapping("/deletePlanById/{planId}")
  public void deletePlanById(@PathVariable String planId) {
    planRepository.deleteById(planId);
  }
}
