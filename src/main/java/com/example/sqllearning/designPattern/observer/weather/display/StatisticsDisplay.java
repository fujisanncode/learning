package com.example.sqllearning.designPattern.observer.weather.display;

import com.example.sqllearning.designPattern.observer.weather.DisplayElement;
import com.example.sqllearning.designPattern.observer.weather.Observer;
import com.example.sqllearning.designPattern.observer.weather.Subject;
import java.text.MessageFormat;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatisticsDisplay implements Observer, DisplayElement {

  private float maxTemp;
  private float minTemp;
  private float avgTemp;

  public StatisticsDisplay(Subject weatherData) {
    // 观察者注册到主题中
    weatherData.registerObserver(this);
  }

  @Override
  public void display() {
    log.info(MessageFormat.format("max/min/avg temperature is {0}/{1}/{2}", maxTemp, minTemp, avgTemp));
  }

  @Override
  public void update(float temp, float humility, float pressure) {
    maxTemp = maxTemp >= temp ? maxTemp : temp;
    // 认为minTemp=0是第一次进入程序， 此时最小值为temp值, 对于avgTemp也按照此逻辑判断是否一次此进入程序
    minTemp = minTemp != 0 && minTemp <= temp ? minTemp : temp;
    avgTemp = avgTemp == 0 ? temp : (avgTemp + temp) / 2;
    display();
  }
}
