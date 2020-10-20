package com.example.sqllearning.designPattern;

import com.example.sqllearning.SqlLearningApplication;
import com.example.sqllearning.designPattern.composite.Menu;
import com.example.sqllearning.designPattern.composite.MenuComponent;
import com.example.sqllearning.designPattern.composite.MenuItem;
import com.example.sqllearning.designPattern.composite.Waitress;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (classes = SqlLearningApplication.class)
public class CompositeTest {

    private Waitress waitress;

    private MenuComponent allMenu;

    @Before
    public void prepareMenu() {
        // 早餐菜单
        MenuComponent pancakeMenu = new Menu("pancake house menu", "breakfast");

        // 午餐菜单
        MenuComponent dinerMenu = new Menu("diner menu", "lunch");

        // 晚餐菜单
        MenuComponent cafeMenu = new Menu("cafe menu", "dinner");
        MenuComponent pasteMenuItem = new MenuItem("鸡", "叫花鸡", 39.99, false);
        MenuComponent dessertMenu = new Menu("dessert menu", "dessert of dinner");
        cafeMenu.add(pasteMenuItem);
        cafeMenu.add(dessertMenu);

        // 晚餐菜单中的甜点菜单
        MenuComponent orangeMenuItem = new MenuItem("橙子", "橙子果盘", 9.99, true);
        dessertMenu.add(orangeMenuItem);

        // 顶级菜单
        this.allMenu = new Menu("all menu", "all menu of combined");
        this.allMenu.add(pancakeMenu);
        this.allMenu.add(dinerMenu);
        this.allMenu.add(cafeMenu);

        // 顶级菜单传递给服务员，通过服务员打印所有的菜单
        this.waitress = new Waitress(this.allMenu);
    }

    @Test
    public void testIterator() {
        this.waitress.printAllMenu();
    }

    @Test
    public void testIterator2() {
        this.waitress.printVegetarian();
    }

    @Test
    public void testIterator3() {
        this.waitress.printAllMenu2();
    }
}
