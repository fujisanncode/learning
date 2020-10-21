package ink.fujisann.learning.base.designPattern.composite;

import java.util.Iterator;

// 始终返还没有下一个元素
public class NullIterator implements Iterator {

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }
}
