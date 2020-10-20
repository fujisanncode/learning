package com.example.sqllearning.designPattern.observer.weather.display;

import com.example.sqllearning.designPattern.observer.weather.DisplayElement;
import com.example.sqllearning.designPattern.observer.weather.Observer;
import com.example.sqllearning.designPattern.observer.weather.Subject;
import java.text.MessageFormat;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrentConditionDisplay implements Observer, DisplayElement {

  // private Subject weatherData;
  private float temp;
  private float humility;

  // 注册观察者
  public CurrentConditionDisplay(Subject weatherData) {
    // 将观察者注册到主题中
    weatherData.registerObserver(this);
    // this.weatherData = weatherData;
  }

  @Override
  public void update(float temp, float humility, float pressure) {
    this.temp = temp;
    this.humility = humility;
    // 更新展板信息
    display();
  }

  @Override
  public void display() {
    log.info(MessageFormat.format("current condition: {0} degree, {1}% humility", temp, humility));
  }
}
