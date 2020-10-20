package ink.fujisann.learning.designPattern.factory.pizzaFactory;

import ink.fujisann.learning.designPattern.factory.integrationFactory.Integration;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

// 披萨的基本制作流程
@Slf4j
public abstract class Pizza {

    // 披萨名称
    public String name;
    // 配料
    public String dough;
    public String sauce;
    public ArrayList<String> toppings = new ArrayList<>();
    // 原料工厂
    public Integration integration;

    public abstract void prepare();

    public void bake() {
        log.info("烘烤披萨");
    }

    public void cut() {
        log.info("切片披萨");
    }

    public void box() {
        log.info("打包披萨");
    }
}

