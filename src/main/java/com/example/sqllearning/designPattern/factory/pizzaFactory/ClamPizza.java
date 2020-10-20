package com.example.sqllearning.designPattern.factory.pizzaFactory;

import com.example.sqllearning.designPattern.factory.integrationFactory.Integration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClamPizza extends Pizza {

    public ClamPizza(Integration integration) {
        this.integration = integration;
    }

    @Override
    public void prepare() {
        dough = integration.createDough();
        log.info("准备蛤蜊披萨的面粉:{}", dough);
        sauce = integration.createSauce();
        log.info("准备蛤蜊披萨的大酱:{}", sauce);
    }

    @Override
    public void cut() {
        log.info("厚切面包");
    }
}
