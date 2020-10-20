package ink.fujisann.learning.designPattern.strategy.behavior.quack;

import ink.fujisann.learning.designPattern.strategy.behavior.QuackBehavior;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Quack implements QuackBehavior {

    @Override
    public void quack() {
        log.info("呱呱叫的行为");
    }
}
