package com.example.sqllearning.designPattern.proxy.cglib;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

// 针对类进行代理，不需要代理类具有接口层
@Slf4j
public class BookCglib implements MethodInterceptor {

    // 保存代理类
    Object object;

    // 返回代理类，目标类是被代理类的子类
    public Object getInstance(Object object) {
        this.object = object;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.object.getClass());
        // 调用被代理类的方法会调用回调方法，回调方法在intercept中实现
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 调用父类方法，即被代理类的方法
        log.info("proxy intercept");
        methodProxy.invokeSuper(o, objects);
        return null;
    }
}
