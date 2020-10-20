package com.example.sqllearning.designPattern.strategy;

import com.example.sqllearning.designPattern.strategy.behavior.fly.FlyWithWings;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MallardDuck extends Duck {

    public MallardDuck() {
        // 绿头鸭默认设置为会飞行
        this.flyBehavior = new FlyWithWings();
    }

    @Override
    public void display() {
        log.info("这是绿头鸭子");
    }
}
