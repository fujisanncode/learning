package ink.fujisann.learning.designPattern.observer.weather;

public interface Observer {

  // 观察者通过传入的数据更新自己
  void update(float temp, float humility, float pressure);

}
