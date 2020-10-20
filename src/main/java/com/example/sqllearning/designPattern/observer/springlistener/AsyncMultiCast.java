package com.example.sqllearning.designPattern.observer.springlistener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

/**
 * @description: 自定义异步的广播
 * @author: hulei
 * @create: 2020-06-07 21:50:00
 */
@Configuration
public class AsyncMultiCast {

  // 输入的bean名称默认是方法名称，可以指定
  @Bean
  public ApplicationEventMulticaster applicationEventMulticaster() {
    SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
    simpleApplicationEventMulticaster.setTaskExecutor(threadPoolExecutorFactoryBean().getObject());
    return simpleApplicationEventMulticaster;
  }

  @Bean
  public ThreadPoolExecutorFactoryBean threadPoolExecutorFactoryBean() {
    ThreadPoolExecutorFactoryBean threadPoolExecutorFactoryBean = new ThreadPoolExecutorFactoryBean();
    threadPoolExecutorFactoryBean.setThreadNamePrefix("event multi cast -");
    threadPoolExecutorFactoryBean.setCorePoolSize(5);
    return threadPoolExecutorFactoryBean;
  }

}
