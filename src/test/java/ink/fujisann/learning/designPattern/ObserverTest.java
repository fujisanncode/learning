package ink.fujisann.learning.designPattern;

import ink.fujisann.learning.LearningApplication;
import ink.fujisann.learning.base.designPattern.observer.weather.display.CurrentConditionDisplay;
import ink.fujisann.learning.base.designPattern.observer.weather.display.CurrentConditionDisplay2;
import ink.fujisann.learning.base.designPattern.observer.weather.display.StatisticsDisplay;
import ink.fujisann.learning.base.designPattern.observer.weather.weatherData.WeatherData;
import ink.fujisann.learning.base.designPattern.observer.weather.weatherData.WeatherData2;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (classes = LearningApplication.class)
public class ObserverTest {

  @Test
  public void testWeatherStation() {
    WeatherData weatherData = new WeatherData();
    CurrentConditionDisplay currentConditionDisplay = new CurrentConditionDisplay(weatherData);
    StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
    // 更新观测数据
    weatherData.setMeasurements(31.2f, 60.5f, 101.3f);
    weatherData.setMeasurements(33.3f, 70.2f, 100.4f);
  }

  @Test
  public void testWeatherStation2() {
    WeatherData2 weatherData2 = new WeatherData2();
    CurrentConditionDisplay2 display2 = new CurrentConditionDisplay2();
    // 主题中注册监听者
    weatherData2.addListener(display2);
    weatherData2.setTemp(20f);
    // 移除监听者
    weatherData2.removeListener(display2);
    weatherData2.setTemp(40f);

  }

}
