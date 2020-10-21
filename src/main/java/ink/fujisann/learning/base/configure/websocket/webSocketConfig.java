package ink.fujisann.learning.base.configure.websocket;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

// 配置ServerEndpointExporter，配置后会自动注册所有“@ServerEndpoint”注解声明的Websocket Endpoint
@Configuration
public class webSocketConfig extends ServerEndpointConfig.Configurator {

  // 修改握手，在建立握手前修改携带的内容，配置shiro进行鉴权
  @Override
  public void modifyHandshake(
      ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
    String curLoginName = (String) SecurityUtils.getSubject().getPrincipal();
    sec.getUserProperties().put("user", curLoginName);
    super.modifyHandshake(sec, request, response);
  }

  // @Bean
  public ServerEndpointExporter enableWebSocket() {
    return new ServerEndpointExporter();
  }
}
