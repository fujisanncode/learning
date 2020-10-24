package ink.fujisann.learning;

import ink.fujisann.learning.code.controller.plan.PlanController;
import ink.fujisann.learning.code.pojo.plan.Plan;
import ink.fujisann.learning.code.repository.PlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @description: 测试mockito
 * @author: hulei
 * @create: 2020-06-12 00:40:56
 */
@Slf4j
@RunWith(MockitoJUnitRunner.class) // 不指定mockito的测试环境，不能通过mockito注入
public class TestMockito {
    @InjectMocks // 返回真实对象
    private PlanController planController;

    @Mock // 不打桩，返回null
    private PlanRepository planRepository;

    @Spy // 不打桩，返回真实对象
    private PlanRepository planRepositorySpy;

    @Test
    public void testPlan() {
        // 模拟findAll接口返回数据
        when(planRepository.findAll()).thenReturn(new ArrayList() {
            {
                add(Plan.builder().title("mockito title").Content("mockito content").build());
            }
        });
        Iterable<Plan> iterable = planController.findAllPlans();
        List<Plan> returnList = new ArrayList();
        iterable.forEach(plan -> returnList.add(plan));
        Assert.assertEquals(1, returnList.size());
    }

    @Test
    public void testSph() {
        Iterable<Plan> iterable = planRepositorySpy.findAll();
        List<Plan> returnList = new ArrayList();
        iterable.forEach(plan -> returnList.add(plan));
        Assert.assertEquals(1, returnList.size());
    }

}
