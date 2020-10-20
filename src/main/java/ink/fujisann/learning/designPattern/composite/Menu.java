package ink.fujisann.learning.designPattern.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

// 菜单 -> 组合元素， 菜单没有价格和是否为素食的判断
@Slf4j
public class Menu extends MenuComponent {

    // 菜单名称
    private String name;
    // 菜单描述
    private String description;
    // 子菜单项
    private List<MenuComponent> childItems = new ArrayList<>();

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void add(MenuComponent component) {
        childItems.add(component);
    }

    @Override
    public void remove(MenuComponent component) {
        childItems.remove(component);
    }

    @Override
    public MenuComponent getChild(int i) {
        return childItems.get(i);
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
    public void print() {
        // 打印菜单
        // 调用菜单的子菜单
        Iterator iterator = childItems.iterator();
        while (iterator.hasNext()) {
            MenuComponent childItem = (MenuComponent) iterator.next();
            childItem.print();
        }
    }

    @Override
    public Iterator createIterator() {
        return new CompositeIterator(childItems.iterator());
    }

    @Override
    public void printOnly() {
        log.info("menu: name -> {}, description -> {}", this.name, this.description);
    }
}
