package ink.fujisann.learning.designPattern.observer.weather.weatherData;

import ink.fujisann.learning.designPattern.observer.weather.Observer;
import ink.fujisann.learning.designPattern.observer.weather.Subject;

import java.util.ArrayList;
import java.util.List;

// 订阅数据来源
public class WeatherData implements Subject {

  // 记录观察查
  private List<Observer> observers;

  // 获取的数据
  private float temp;
  private float humility;
  private float pressure;

  public WeatherData() {
    observers = new ArrayList<>();
  }

  @Override
  public void registerObserver(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(Observer observer) {
    int index = observers.indexOf(observer);
    if (index > 0) {
      observers.remove(index);
    }
  }

  @Override
  public void notifyObserver() {
    for (Observer observer : observers) {
      observer.update(temp, humility, pressure);
    }
  }

  // 当数据变化 通知观察者
  public void measurementChanged() {
    notifyObserver();
  }

  // 模拟获取数据
  public void setMeasurements(float temp, float humility, float pressure) {
    this.temp = temp;
    this.humility = humility;
    this.pressure = pressure;
    measurementChanged();
  }
}
