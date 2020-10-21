package ink.fujisann.learning.base.designPattern.observer.listener;

import ink.fujisann.learning.base.designPattern.observer.listener.listener.EventListener;
import ink.fujisann.learning.base.designPattern.observer.listener.multicast.EventMultiCast;
import ink.fujisann.learning.base.designPattern.observer.listener.multicast.SimpleEventMultiCast;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 广播和用户注册服务注入到容器中
 * @author: hulei
 * @create: 2020-06-07 17:24:21
 */
@Configuration
public class EventConfig {

  @Bean
  public EventMultiCast getEventMultiCast(List<EventListener> eventListenerList) {
    EventMultiCast eventMultiCast = new SimpleEventMultiCast();
    eventListenerList.forEach(eventMultiCast::addListener);
    return eventMultiCast;
  }

  @Bean
  public UserRegisterService getUserRegisterService(EventMultiCast eventMultiCast) {
    UserRegisterService userRegisterService = new UserRegisterService(eventMultiCast);
    return userRegisterService;
  }
}
