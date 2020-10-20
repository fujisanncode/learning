package com.example.sqllearning.designPattern.strategy.behavior.fly;

import com.example.sqllearning.designPattern.strategy.behavior.FlyBehavior;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlyWithWings implements FlyBehavior {

    @Override
    public void fly() {
        log.info("会飞的行为");
    }
}
