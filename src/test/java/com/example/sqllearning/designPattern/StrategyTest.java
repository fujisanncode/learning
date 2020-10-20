package com.example.sqllearning.designPattern;

import com.example.sqllearning.SqlLearningApplication;
import com.example.sqllearning.designPattern.strategy.MallardDuck;
import com.example.sqllearning.designPattern.strategy.behavior.fly.FlyNoWay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith (SpringRunner.class) // 指定runner，不同的runner具备特殊的功能
@SpringBootTest (classes = SqlLearningApplication.class) // 指定启动类
public class StrategyTest {

    @Test
    public void testChangeFly() {
        // 默认会飞翔的绿头鸭子
        MallardDuck mallardDuck = new MallardDuck();
        mallardDuck.display();
        mallardDuck.performFly();
        // 设定绿头鸭为不会飞翔
        mallardDuck.setFlyBehavior(new FlyNoWay());
        mallardDuck.performFly();
    }
}
