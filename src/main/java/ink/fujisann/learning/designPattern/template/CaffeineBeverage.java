package ink.fujisann.learning.designPattern.template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CaffeineBeverage {

    // 通过模板算法实现制作咖啡因饮料的步骤,final不能被子类覆盖
    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if (hook()) { addCondiments(); }
    }

    public void boilWater() {
        log.info("烧水");
    }

    // 冲泡方法(泡咖啡或者泡茶)
    abstract void brew();

    public void pourInCup() {
        log.info("倒入杯中");
    }

    // 增加调料(加糖或者加柠檬)
    abstract void addCondiments();

    // 增加钩子方法，增加模板方法可控
    public boolean hook() {
        return true;
    }
}
