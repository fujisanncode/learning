package com.example.sqllearning.designPattern.observer.weather.weatherData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class WeatherData2 {

  // 主题中的数据
  private float temp;

  // 注册观察者， 通知观察者
  private PropertyChangeSupport subject = new PropertyChangeSupport(this);

  public float getTemp() {
    return temp;
  }

  // 将温度的变化通知给观察着， 并且记录温度
  public void setTemp(float temp) {
    firePropertyChange("temp", this.temp, temp);
    this.temp = temp;
  }

  // 注册观察者
  public void addListener(PropertyChangeListener propertyChangeListener) {
    this.subject.addPropertyChangeListener(propertyChangeListener);
  }

  // 移除观察者
  public void removeListener(PropertyChangeListener propertyChangeListener) {
    this.subject.removePropertyChangeListener(propertyChangeListener);
  }

  // 主题中属性改变(通知所有注册的观察者)
  private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    this.subject.firePropertyChange(propertyName, oldValue, newValue);
  }

}
