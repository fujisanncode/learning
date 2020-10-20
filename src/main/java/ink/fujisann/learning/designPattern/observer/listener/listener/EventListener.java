package ink.fujisann.learning.designPattern.observer.listener.listener;

import ink.fujisann.learning.designPattern.observer.listener.event.AbstractEvent;

/**
 * @description: 事件监听器
 * @author: hulei
 * @create: 2020-06-07 16:31:40
 */
public interface EventListener<E extends AbstractEvent> {

  void onEvent(AbstractEvent event);
}
