package com.example.sqllearning.designPattern.factory.pizzaStore;

import com.example.sqllearning.designPattern.factory.integrationFactory.Integration;
import com.example.sqllearning.designPattern.factory.integrationFactory.ItIntegration;
import com.example.sqllearning.designPattern.factory.pizzaFactory.CheesePizza;
import com.example.sqllearning.designPattern.factory.pizzaFactory.ClamPizza;
import com.example.sqllearning.designPattern.factory.pizzaFactory.Pizza;

public class ItPizzaStore extends PizzaStore {

    @Override
    public Pizza createPizza(String type) {
        Integration integration = new ItIntegration();
        Pizza pizza = null;
        if ("cheese".equals(type)) {
            pizza = new CheesePizza(integration);
        } else if ("clam".equals(type)) {
            pizza = new ClamPizza(integration);
        }
        return pizza;
    }
}
