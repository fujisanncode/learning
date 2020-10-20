package ink.fujisann.learning.designPattern;

import ink.fujisann.learning.LearningApplication;
import ink.fujisann.learning.designPattern.decorator.Beverage;
import ink.fujisann.learning.designPattern.decorator.Espresso;
import ink.fujisann.learning.designPattern.decorator.Mocha;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest (classes = LearningApplication.class)
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
