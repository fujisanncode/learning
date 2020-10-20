package com.example.sqllearning.designPattern;

import com.example.sqllearning.SqlLearningApplication;
import com.example.sqllearning.designPattern.decorator.Beverage;
import com.example.sqllearning.designPattern.decorator.Espresso;
import com.example.sqllearning.designPattern.decorator.Mocha;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest (classes = SqlLearningApplication.class)
public class DecoratorTest {

    @Test
    public void starBuzz() {
        // 基本的浓缩咖啡
        Beverage beverage = new Espresso();
        log.info(beverage.getDescription().concat(" $").concat(beverage.cost() + ""));

        // 浓缩咖啡增加两杯摩卡
        Beverage beverage1 = new Espresso();
        beverage1 = new Mocha(beverage1);
        beverage1 = new Mocha(beverage1);
        log.info(beverage1.getDescription().concat(" $").concat(beverage1.cost() + ""));
    }
}
