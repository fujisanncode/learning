package ink.fujisann.learning.designPattern.factory.pizzaStore;

import ink.fujisann.learning.designPattern.factory.integrationFactory.Integration;
import ink.fujisann.learning.designPattern.factory.integrationFactory.ItIntegration;
import ink.fujisann.learning.designPattern.factory.pizzaFactory.CheesePizza;
import ink.fujisann.learning.designPattern.factory.pizzaFactory.ClamPizza;
import ink.fujisann.learning.designPattern.factory.pizzaFactory.Pizza;

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
