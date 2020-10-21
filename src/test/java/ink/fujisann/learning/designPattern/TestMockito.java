package ink.fujisann.learning.designPattern;

import ink.fujisann.learning.code.controller.plan.PlanController;
import ink.fujisann.learning.code.repository.PlanRepository;
import ink.fujisann.learning.code.vo.plan.Plan;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 通过mockito加载依赖运行单元测试
 * @author: hulei
 * @create: 2020-06-07 20:18:06
 */
@Slf4j
public class TestMockito {

  @InjectMocks
  private PlanController planController = new PlanController();

  @Mock // PlanRepository需要模拟出来
  private PlanRepository planRepository;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    // 设置需要模拟的函数，当遇到When中函数，按照return中模拟的函数返回
    Mockito.when(planRepository.findAll()).thenReturn(new ArrayList<Plan>() {{
      add(Plan.builder().title("mock plan").build());
    }});
  }

  @Test
  public void testFindPlans() {
    Iterable<Plan> planIterable = planController.findAllPlans();
    List planList = new ArrayList();
    planIterable.forEach(e -> {
      planList.add(e);
    });
    log.info(planList.toString());
    Assert.assertEquals(1, planList.size());
  }

}
