package ink.fujisann.learning.base.designPattern.composite;

import java.util.Iterator;
import java.util.Stack;

// 组合迭代器可以遍历当前节点以及所有的子节点（所有深层子节点），用于遍历树形结构
public class CompositeIterator implements Iterator {

    // 用堆栈保存迭代器，以及所有子元素（子元素以及深层子元素）的迭代器
    Stack stack = new Stack();

    public CompositeIterator(Iterator iterator) {
        stack.push(iterator);
    }

    // 对于堆栈中遍历结束的迭代器需要冲迭代器中移除
    @Override
    public boolean hasNext() {
        if (stack.empty()) {
            return false;
        } else {
            // 如果堆栈不为空，从堆栈中取出迭代器
            Iterator iterator = (Iterator) stack.peek();
            if (iterator.hasNext()) {
                // 如果迭代其中有元素，返回true
                return true;
            } else {
                // 如果迭代其中没有元素，将此迭代器从堆栈中移除，重新判断堆栈中是否还有迭代器
                stack.pop();
                return this.hasNext();
            }
        }
    }

    // 如果当前迭代的元素是节点元素，将节点元素自己的迭代器（用于迭代子元素），放进堆栈中
    @Override
    public Object next() {
        if (this.hasNext()) {
            // peek返回栈顶值，但是不删除返回的元素；pop返回栈顶元素同时删除这个元素
            Iterator iterator = (Iterator) stack.peek();
            MenuComponent menu = (MenuComponent) iterator.next();
            // 如果迭代器迭代到节点元素，则将节点元素的迭代器推入栈中
            if (menu instanceof Menu) {
                stack.push(menu.createIterator());
            }
            return menu;
        } else {
            return null;
        }
    }
}
