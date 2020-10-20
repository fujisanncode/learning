package ink.fujisann.learning.designPattern.strategy;

import ink.fujisann.learning.designPattern.strategy.behavior.FlyBehavior;
import ink.fujisann.learning.designPattern.strategy.behavior.QuackBehavior;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Duck {

    // 接口行为，通过具体的实现类表现
    public FlyBehavior flyBehavior;
    public QuackBehavior quackBehavior;

    // 设定行为
    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

    // 表现具体的行为
    public void performFly() {
        flyBehavior.fly();
    }

    public void performQuack() {
        quackBehavior.quack();
    }

    // 所有鸭子的共性行为，不需要动态改变的行为
    public void swim() {
        log.info("all duck can swim.");
    }

    // 抽象方法，需要在不同的子类中去实现
    public abstract void display();
}

