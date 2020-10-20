package com.example.sqllearning.designPattern.singleton;

public class SimpleSingleton {

    // 或者在类初始化的时候进行实例化，可以避免多线程下产生多个实例
    // private static SimpleSingleton simpleSingleton = new SimpleSingleton();
    // volatile同步变量，配合synchronized使用
    private static volatile SimpleSingleton simpleSingleton;

    private SimpleSingleton() {

    }

    // 需要实例的时候调用 仅创建一次实例 或者在方法上加上synchronize关键词（存在性能问题）
    public static SimpleSingleton getInstance() {
        // 双重检查锁，保证变量的延迟创建，以及避免多线程下多个实例的问题，并且保证无性能问题(同步代码块仅执行一次)
        if (null == simpleSingleton) {
            // 线程1、2为null，1先进入，2进入时变量已经复赋值
            synchronized (SimpleSingleton.class) {
                if (null == simpleSingleton) {
                    simpleSingleton = new SimpleSingleton();
                }
            }
        }
        return simpleSingleton;
    }
}
