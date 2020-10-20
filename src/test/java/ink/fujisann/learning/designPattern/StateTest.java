package ink.fujisann.learning.designPattern;

import ink.fujisann.learning.LearningApplication;
import ink.fujisann.learning.designPattern.state.GumballMachine;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

// 策略模式
@SpringBootTest (classes = LearningApplication.class)
public class StateTest {

    @Test
    public void testGumballMachine() {
        GumballMachine gumballMachine = new GumballMachine(10);
        for (int i = 0; i < 10; i++) {
            gumballMachine.insertQuarter();
            gumballMachine.turnCrank();
        }
    }
}
