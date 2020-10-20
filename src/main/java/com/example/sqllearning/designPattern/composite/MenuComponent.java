package com.example.sqllearning.designPattern.composite;

import java.util.Iterator;

// 组件抽象类 组合模式中的元素（组合、叶结点）
public abstract class MenuComponent {

    // 添加元素
    public void add(MenuComponent component) {
        throw new UnsupportedOperationException();
    }

    // 删除元素
    public void remove(MenuComponent component) {
        throw new UnsupportedOperationException();
    }

    // 获取第i个子元素
    public MenuComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    // 获取菜单名称
    public String getName() {
        throw new UnsupportedOperationException();
    }

    // 获取菜单描述
    public String getDescription() {
        throw new UnsupportedOperationException();
    }

    // 获取菜单价格
    public double getPrice() {
        throw new UnsupportedOperationException();
    }

    // 菜单是否为素食
    public boolean isVegetarian() {
        throw new UnsupportedOperationException();
    }

    // 打印菜单
    public void print() {
        throw new UnsupportedOperationException();
    }

    // 只打印当前节点，不打印子节点
    public void printOnly() {
        throw new UnsupportedOperationException();
    }

    // 通过迭代器直接遍历所有菜单和菜单子项
    public Iterator createIterator() {
        throw new UnsupportedOperationException();
    }
}
