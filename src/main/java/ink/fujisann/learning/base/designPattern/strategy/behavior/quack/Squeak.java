package ink.fujisann.learning.base.designPattern.strategy.behavior.quack;

import ink.fujisann.learning.base.designPattern.strategy.behavior.QuackBehavior;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Squeak implements QuackBehavior {

    @Override
    public void quack() {
        log.info("吱吱叫的行为");
    }
}
