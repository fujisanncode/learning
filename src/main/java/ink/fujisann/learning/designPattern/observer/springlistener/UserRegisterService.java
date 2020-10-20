package ink.fujisann.learning.designPattern.observer.springlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @description: 发布事件
 * @author: hulei
 * @create: 2020-06-07 21:22:54
 */
@Slf4j
@Component // 注入到容器中， 方便后面注入调用
public class UserRegisterService implements ApplicationEventPublisherAware {

  private ApplicationEventPublisher applicationEventPublisher;

  public void register() {
    log.info("用户注册成功");
    applicationEventPublisher.publishEvent(new UserRegisterEvent(this));
  }

  // 注入广播
  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }
}
