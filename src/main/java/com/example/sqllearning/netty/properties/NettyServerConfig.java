package com.example.sqllearning.netty.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description: 读取property文件的第一种方式
 * @author: hulei
 * @create: 2020-06-10 21:41:02
 */
@Component
@Data
public class NettyServerConfig {

  @Value("${netty.server.host}")
  private String host;

  @Value("${netty.server.port}")
  private Integer port;
}
