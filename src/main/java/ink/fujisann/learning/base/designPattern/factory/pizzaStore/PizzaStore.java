package ink.fujisann.learning.base.designPattern.factory.pizzaStore;

import ink.fujisann.learning.base.designPattern.factory.pizzaFactory.Pizza;

public abstract class PizzaStore {

    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

    public abstract Pizza createPizza(String type);
}
