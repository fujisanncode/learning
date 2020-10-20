package com.example.sqllearning.designPattern;

import com.example.sqllearning.SqlLearningApplication;
import com.example.sqllearning.designPattern.factory.pizzaStore.ItPizzaStore;
import com.example.sqllearning.designPattern.factory.pizzaStore.NyPizzaStore;
import com.example.sqllearning.designPattern.factory.pizzaStore.PizzaStore;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (classes = SqlLearningApplication.class)
public class FactoryTest {

    @Test
    public void testPizza() {
        PizzaStore nyPizzaStore = new NyPizzaStore();
        nyPizzaStore.orderPizza("cheese");
        PizzaStore itPizzaStore = new ItPizzaStore();
        itPizzaStore.orderPizza("cheese");
    }
}
