package ink.fujisann.learning;

import ink.fujisann.learning.base.configure.shiro.property.ShiroProperty;
import ink.fujisann.learning.base.netty.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类<br/>
 * {@code @EnableAsync} 开启异步<br/>
 * {@code @EnableScheduling} 开启定时任务<br/>
 * {@code @MapperScan} dao层不需要注解 dao通过下面的注解自动注入容器<br/>
 *
 * @author hulei
 * @date 2020/11/2
 */
@Slf4j
@MapperScan("ink.fujisann.learning.code.dao")
@SpringBootApplication(scanBasePackages = "ink.fujisann")
@EnableAsync
@EnableConfigurationProperties(value = {ShiroProperty.class})
@EnableScheduling
public class LearningApplication implements CommandLineRunner {

  public static void main(String[] args) {
    // 启动webServer
    ApplicationContext applicationContext =
            SpringApplication.run(LearningApplication.class, args);
    // 配置nettyServer
    applicationContext.getBean(NettyServer.class).start();
    // new NettyServer().start(); // new 出来的对象中不能注入
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("进入run方法");
  }
}
