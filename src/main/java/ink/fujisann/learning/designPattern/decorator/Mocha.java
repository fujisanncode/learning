package ink.fujisann.learning.designPattern.decorator;

public class Mocha extends Condiment {

    Beverage beverage;

    // 通过基本的饮料，继续增加配料
    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription().concat(", Mocha");
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.99;
    }
}
