package ink.fujisann.learning.base.designPattern.observer.weather.display;

import ink.fujisann.learning.base.designPattern.observer.weather.DisplayElement;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrentConditionDisplay2 implements PropertyChangeListener, DisplayElement {

  private float temp;

  public CurrentConditionDisplay2() {

  }

  @Override
  public void display() {
    log.info("current temp is {} degree", temp);
  }

  @Override
  public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
    if ("temp".equals(propertyChangeEvent.getPropertyName())) {
      this.temp = (float) propertyChangeEvent.getNewValue();
      display();
    }
  }
}
