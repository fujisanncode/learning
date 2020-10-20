package ink.fujisann.learning.netty.properties;

import lombok.Data;

/**
 * @description: netty server 的配置文件
 * @author: hulei
 * @create: 2020-06-10 21:56:57
 */
@Data
public class NettyServerProperty {

  private String host;
  private Integer port;
}
