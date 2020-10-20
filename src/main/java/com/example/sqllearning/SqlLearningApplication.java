package com.example.sqllearning;

import com.example.sqllearning.configure.shiro.property.ShiroProperty;
import com.example.sqllearning.netty.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@MapperScan("com.example.sqllearning.dao") // dao层不需要注解 dao通过下面的注解自动注入容器
@SpringBootApplication(scanBasePackages = "com.example")
@EnableAsync // 开启异步
@EnableConfigurationProperties(value = {ShiroProperty.class})
@EnableScheduling // 开启定时任务
public class SqlLearningApplication implements CommandLineRunner {

  public static void main(String[] args) {
    // 启动webServer
    ApplicationContext applicationContext =
        SpringApplication.run(SqlLearningApplication.class, args);
    // 配置nettyServer
    applicationContext.getBean(NettyServer.class).start();
    // new NettyServer().start(); // new 出来的对象中不能注入
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("进入run方法");
  }
}
