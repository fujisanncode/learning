package ink.fujisann.learning.designPattern.strategy.behavior.fly;

import ink.fujisann.learning.designPattern.strategy.behavior.FlyBehavior;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlyWithWings implements FlyBehavior {

    @Override
    public void fly() {
        log.info("会飞的行为");
    }
}
