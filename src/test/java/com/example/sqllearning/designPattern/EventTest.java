package com.example.sqllearning.designPattern;

import com.example.sqllearning.SqlLearningApplication;
import com.example.sqllearning.designPattern.observer.listener.UserRegisterService;
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
@SpringBootTest (classes = SqlLearningApplication.class)
public class EventTest {

  @Autowired
  private UserRegisterService userRegisterService;

  @Autowired
  private com.example.sqllearning.designPattern.observer.springlistener.UserRegisterService springRegisterService;

  @Test
  public void register() {
    userRegisterService.register();
  }

  @Test
  public void testSpringEvent() {
    springRegisterService.register();
  }

}
