package ink.fujisann.learning.designPattern.observer.listener.multicast;

import ink.fujisann.learning.designPattern.observer.listener.event.AbstractEvent;
import ink.fujisann.learning.designPattern.observer.listener.listener.EventListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 默认的事件广播器实现
 * @author: hulei
 * @create: 2020-06-07 16:35:53
 */
public class SimpleEventMultiCast implements EventMultiCast {

  // 事件和事件对应监听器的映射关系
  private Map<Class, List<EventListener>> eventMapListener = new ConcurrentHashMap<>();

  @Override
  public void multiCastEvent(AbstractEvent event) {
    // 事件播报到其对应的所有监听器中
    List<EventListener> eventListenerList = eventMapListener.get(event.getClass());
    for (EventListener eventListenerListTmp : eventListenerList) {
      eventListenerListTmp.onEvent(event);
    }
  }

  @Override
  public void addListener(EventListener eventListener) {
    Class event = getEventType(eventListener);
    List<EventListener> eventListenerList = eventMapListener.get(event);
    if (eventListenerList == null) {
      eventListenerList = new ArrayList<>();
    }
    eventListenerList.add(eventListener);
    eventMapListener.put(event, eventListenerList);
  }

  @Override
  public void removeListener(EventListener eventListener) {
    Class event = getEventType(eventListener);
    eventMapListener.remove(event);
  }

  // 根据监听器获取监听器对应的事件类型
  public Class getEventType(EventListener eventListener) {
    // 获取接口定义的信息(包含泛型信息)
    ParameterizedType parameterizedType = (ParameterizedType) eventListener.getClass().getGenericInterfaces()[0];
    // 获取上述所有信息中泛型具体的类型 即E
    Type type = parameterizedType.getActualTypeArguments()[0];
    return (Class) type;
  }
}
