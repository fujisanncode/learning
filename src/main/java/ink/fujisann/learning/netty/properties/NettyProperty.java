package ink.fujisann.learning.netty.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: 读取property文件的第二种方式
 * @author: hulei
 * @create: 2020-06-10 21:53:57
 */
@Component // 注入容器
@ConfigurationProperties(prefix = "netty") // 配置前缀
@Data
public class NettyProperty {

  private NettyServerProperty server; // 通过对象配置子层级属性
}
