package ink.fujisann.learning.designPattern;

import ink.fujisann.learning.LearningApplication;
import ink.fujisann.learning.designPattern.proxy.cglib.BookCglib;
import ink.fujisann.learning.designPattern.proxy.cglib.BookImpl;
import ink.fujisann.learning.designPattern.proxy.dynmic.MyInvocationHandler;
import ink.fujisann.learning.designPattern.proxy.dynmic.RealSubject;
import ink.fujisann.learning.designPattern.proxy.dynmic.Subject;
import ink.fujisann.learning.designPattern.proxy.stati.Cloth;
import ink.fujisann.learning.designPattern.proxy.stati.NikeCloth;
import ink.fujisann.learning.designPattern.proxy.stati.ProxyCloth;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (classes = LearningApplication.class)
public class proxyTest {

    @Test
    public void dynamic() {
        RealSubject real = new RealSubject();
        MyInvocationHandler handler = new MyInvocationHandler();
        // 通过bind方法返回一个subject接口类型的对象
        Object object = handler.bind(real);
        Subject subject = (Subject) object;
        // 调用到代理类的invoke方法
        subject.action();
    }

    @Test
    public void staticProxy() {
        Cloth cloth = new NikeCloth();
        ProxyCloth proxy = new ProxyCloth(cloth);
        cloth.getName();
    }

    @Test
    public void cglibProxy() {
        BookImpl book = new BookImpl();
        BookCglib cglib = new BookCglib();
        // 代理类是BookImpl的子类
        BookImpl proxy = (BookImpl) cglib.getInstance(book);
        proxy.add();
    }
}
