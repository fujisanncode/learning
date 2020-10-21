package ink.fujisann.learning.base.designPattern.factory.pizzaStore;

import ink.fujisann.learning.base.designPattern.factory.integrationFactory.Integration;
import ink.fujisann.learning.base.designPattern.factory.integrationFactory.ItIntegration;
import ink.fujisann.learning.base.designPattern.factory.pizzaFactory.ClamPizza;
import ink.fujisann.learning.base.designPattern.factory.pizzaFactory.CheesePizza;
import ink.fujisann.learning.base.designPattern.factory.pizzaFactory.Pizza;
import lombok.extern.slf4j.Slf4j;

// 子类中实现pizza工厂
@Slf4j
public class NyPizzaStore extends PizzaStore {

    @Override
    public Pizza createPizza(String type) {
        Integration integration = new ItIntegration();
        // 局部变量必须初始化，成员变量没有final修饰存在默认初始化， final修饰必须初始化
        Pizza pizza = null;
        if ("cheese".equals(type)) {
            pizza = new CheesePizza(integration);
        } else if ("clam".equals(type)) {
            pizza = new ClamPizza(integration);
        }
        return pizza;
    }
}
