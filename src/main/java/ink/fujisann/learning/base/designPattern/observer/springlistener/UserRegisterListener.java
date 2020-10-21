package ink.fujisann.learning.base.designPattern.observer.springlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @description: 监听用户注册事件
 * @author: hulei
 * @create: 2020-06-07 21:12:36
 */
@Component // 此类增加被扫描的标志
@Slf4j
public class UserRegisterListener {

  @EventListener // 监听参数中的事件
  @Order (0) // 指定监听器的顺序
  public void sendMail(UserRegisterEvent userRegisterEvent) {
    log.info("用户注册后，发送邮件");
  }

  @EventListener
  @Order (1)
  public void addScore(UserRegisterEvent userRegisterEvent) {
    log.info("用户注册后，增加用户积分");
  }
}
