package ink.fujisann.learning.designPattern.decorator;

public abstract class Beverage {

    String description = "Unknown beverage";

    public String getDescription() {
        return description;
    }

    // 计算饮料价格
    public abstract double cost();
}
