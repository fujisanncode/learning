package com.example.sqllearning.designPattern.proxy.dynmic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import lombok.extern.slf4j.Slf4j;

// 动态代理类，被代理的类必须实现接口， 并且代理类只能代理接口层定义的方法
@Slf4j
public class MyInvocationHandler implements InvocationHandler {

    // 被代理类的对象申明
    Object obj;

    // 1、被代理类的对象实例化 2、返回一个代理类的对象（通过代理类调用被代理类的方法会调用到下面的invoke方法）
    public Object bind(Object obj) {
        this.obj = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
    }

    // 通过代理类调用重写方法会进入此方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("invokeHandler -> invoke");
        // this.obj 中绑定了被代理类对象，method.invoke会调用到被代理类的方法
        // 通过代理类访问被代理类，bind方法返回一个代理类(实际类型是被代理类的接口层，所以被代理类必须有接口层)
        Object returnVal = method.invoke(this.obj, args);
        return returnVal;
    }
}
