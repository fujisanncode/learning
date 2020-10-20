package ink.fujisann.learning.designPattern;

import ink.fujisann.learning.LearningApplication;
import ink.fujisann.learning.designPattern.factory.pizzaStore.ItPizzaStore;
import ink.fujisann.learning.designPattern.factory.pizzaStore.NyPizzaStore;
import ink.fujisann.learning.designPattern.factory.pizzaStore.PizzaStore;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (classes = LearningApplication.class)
public class FactoryTest {

    @Test
    public void testPizza() {
        PizzaStore nyPizzaStore = new NyPizzaStore();
        nyPizzaStore.orderPizza("cheese");
        PizzaStore itPizzaStore = new ItPizzaStore();
        itPizzaStore.orderPizza("cheese");
    }
}
