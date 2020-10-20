package com.example.sqllearning.designPattern.decorator;

// 浓缩咖啡
public class Espresso extends Beverage {

    public Espresso() {
        description = "浓缩咖啡";
    }

    @Override
    public double cost() {
        return 0.99;
    }
}
