package com.example.sqllearning.thread;

/**
 * @description: 测试多线程
 * @author: hulei
 * @create: 2020-09-03 22:16:59
 */
public class ThreadTest2 extends Thread {
  public static void main(String[] args) {
    Thread thread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                for (int i = 0; i < 50000; i++) {
                  System.out.println(i);
                  if (Thread.interrupted()) {
                    System.out.println("线程即将中断");
                    return;
                  }
                }
                System.out.println("xxxx");
              }
            });
    thread.start();
    System.out.println("yyyy");
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    thread.interrupt();
  }

  public void aa() {
    this.currentThread();
  }
}
