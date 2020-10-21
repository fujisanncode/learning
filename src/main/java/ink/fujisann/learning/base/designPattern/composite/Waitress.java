package ink.fujisann.learning.base.designPattern.composite;

import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;

// 服务员类，通过服务员可以获取所有的菜单
@Slf4j
public class Waitress {

    // 顶级菜单
    private MenuComponent menu;

    public Waitress(MenuComponent menu) {
        this.menu = menu;
    }

    // 调用顶级菜单的打印方法
    public void printAllMenu() {
        this.menu.print();
    }

    // 遍历所有子层菜单，然后打印素食菜单项
    public void printVegetarian() {
        Iterator iterator = menu.createIterator();
        while (iterator.hasNext()) {
            MenuComponent item = (MenuComponent) iterator.next();
            try {
                if (item.isVegetarian()) {
                    item.print();
                }
            } catch (UnsupportedOperationException e) {
                // log.error("print vegetarian: ", e);
            }
        }
    }

    // 利用顶层菜单的迭代器遍历所有菜单
    public void printAllMenu2() {
        Iterator iterator = menu.createIterator();
        while (iterator.hasNext()) {
            MenuComponent item = (MenuComponent) iterator.next();
            item.printOnly();
        }
    }
}
