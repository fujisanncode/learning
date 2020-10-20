package ink.fujisann.learning.designPattern;

import ink.fujisann.learning.LearningApplication;
import ink.fujisann.learning.designPattern.observer.listener.UserRegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description: 测试事件监听
 * @author: hulei
 * @create: 2020-06-07 17:29:24
 */
@RunWith (SpringRunner.class) // 加载spring依赖
@SpringBootTest (classes = LearningApplication.class)
public class EventTest {

  @Autowired
  private UserRegisterService userRegisterService;

  @Autowired
  private UserRegisterService springRegisterService;

  @Test
  public void register() {
    userRegisterService.register();
  }

  @Test
  public void testSpringEvent() {
    springRegisterService.register();
  }

}
