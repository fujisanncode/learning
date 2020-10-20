package com.example.sqllearning.designPattern.observer.listener.multicast;

import com.example.sqllearning.designPattern.observer.listener.event.AbstractEvent;
import com.example.sqllearning.designPattern.observer.listener.listener.EventListener;

/**
 * @description: 事件广播器
 * @author: hulei
 * @create: 2020-06-07 16:32:47
 */
public interface EventMultiCast {

  void multiCastEvent(AbstractEvent event);

  void addListener(EventListener eventListener);

  void removeListener(EventListener eventListener);
}
