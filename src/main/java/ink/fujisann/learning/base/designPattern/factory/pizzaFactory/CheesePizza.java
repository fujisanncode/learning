package ink.fujisann.learning.base.designPattern.factory.pizzaFactory;

import ink.fujisann.learning.base.designPattern.factory.integrationFactory.Integration;
import lombok.extern.slf4j.Slf4j;

// 特定的披萨
@Slf4j
public class CheesePizza extends Pizza {


    public CheesePizza(Integration integration) {
        this.integration = integration;
    }

    @Override
    public void prepare() {
        dough = integration.createDough();
        log.info("准备芝士披萨的面粉:{}", dough);
        sauce = integration.createSauce();
        log.info("准备芝士披萨的大酱:{}", sauce);
    }
}
