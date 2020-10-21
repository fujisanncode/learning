package ink.fujisann.learning.base.designPattern.observer.weather;

public interface Subject {

  // 观察者注册
  void registerObserver(Observer observer);

  // 观察者取消注册
  void removeObserver(Observer observer);

  // 推送消息给观察者
  void notifyObserver();

}
