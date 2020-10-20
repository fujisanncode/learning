package com.example.sqllearning.designPattern.strategy.behavior.quack;

import com.example.sqllearning.designPattern.strategy.behavior.QuackBehavior;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Quack implements QuackBehavior {

    @Override
    public void quack() {
        log.info("呱呱叫的行为");
    }
}
