package ink.fujisann.learning.designPattern.observer.listener;

import ink.fujisann.learning.designPattern.observer.listener.event.UserRegisterEvent;
import ink.fujisann.learning.designPattern.observer.listener.multicast.EventMultiCast;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 用户注册服务
 * @author: hulei
 * @create: 2020-06-07 16:41:46
 */
@Slf4j
public class UserRegisterService {

  private EventMultiCast eventMultiCast;

  public UserRegisterService(EventMultiCast eventMultiCast) {
    this.eventMultiCast = eventMultiCast;
  }

  public void register() {
    log.info("用户注册成功");
    this.eventMultiCast.multiCastEvent(new UserRegisterEvent());
  }

}
