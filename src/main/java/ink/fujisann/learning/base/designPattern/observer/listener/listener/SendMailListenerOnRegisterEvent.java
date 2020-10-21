package ink.fujisann.learning.base.designPattern.observer.listener.listener;

import ink.fujisann.learning.base.designPattern.observer.listener.event.AbstractEvent;
import ink.fujisann.learning.base.designPattern.observer.listener.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: 注册用户后触发发送邮件的监听器
 * @author: hulei
 * @create: 2020-06-07 17:15:23
 */
@Slf4j
@Component
public class SendMailListenerOnRegisterEvent implements EventListener<UserRegisterEvent> {

  @Override
  public void onEvent(AbstractEvent event) {
    log.info("用户注册后，触发发送邮件");
  }
}
