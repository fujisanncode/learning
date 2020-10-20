package com.example.sqllearning.designPattern.composite;

import lombok.extern.slf4j.Slf4j;

// 菜单项 -> 叶结点, 叶节点没有子元素
@Slf4j
public class MenuItem extends MenuComponent {

    private String name;
    private String description;
    private double price;
    private boolean vegetarian;

    public MenuItem(String name, String description, double price, boolean vegetarian) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.vegetarian = vegetarian;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public boolean isVegetarian() {
        return this.vegetarian;
    }

    @Override
    public void print() {
        log.info("menuItem: name -> {}, description ->{}, price -> {}, isVegetarian -> {}", this.name, this.description, this.price,
            this.isVegetarian());
    }

    @Override
    public void printOnly() {
        log.info("menuItem: name -> {}, description ->{}, price -> {}, isVegetarian -> {}", this.name, this.description, this.price,
            this.isVegetarian());
    }
}
