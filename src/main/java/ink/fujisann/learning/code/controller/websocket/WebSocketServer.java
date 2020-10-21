package ink.fujisann.learning.code.controller.websocket;

import ink.fujisann.learning.base.configure.websocket.webSocketConfig;
import io.swagger.annotations.Api;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint (value = "/websocket/{cur-user-name}/{to-user-name}", configurator = webSocketConfig.class)
@Api ("websocket server")
@Slf4j
public class WebSocketServer {

    // 线程安全的list，保存每个客户端的连接
    private static CopyOnWriteArrayList<WebSocketServer> servers = new CopyOnWriteArrayList<>();
    // 在线人数
    private int onlineCount;
    // 客户端连接时记录session
    private Session session;
    // 谁发消息
    private String curUserName;
    // 发消息给谁
    private String toUserName;

    // 推送指定消息
    public static void sendInfo(String msg, @PathParam ("sessionId") String sessionId) {
        log.info("给 {} 推送消息 {}", sessionId, msg);
        try {
            for (WebSocketServer server : servers) {
                // 如果指定了sessionId，则指定给特定的连接，否则全部推送
                if (StringUtils.isEmpty(sessionId)) {
                    server.sendMsg(msg);
                } else if (sessionId.equals(server.curUserName)) {
                    server.sendMsg(msg);
                }
            }
        } catch (IOException e) {
            log.error("web socket error {}", e);
        }
    }

    // 给客户端推送消息
    public void sendMsg(String msg) throws IOException {
        this.session.getBasicRemote().sendText(msg);
    }

    // 建立连接
    @OnOpen
    public void onOpen(Session session, @PathParam ("cur-user-name") String curUserName, @PathParam ("to-user-name") String toUserName) {
        // 设置websocket服务端的超时断开时间
        session.setMaxIdleTimeout(10 * 60 * 1000);
        this.session = session;
        String loginName = (String) session.getUserProperties().get("user");
        this.curUserName = loginName;
        this.toUserName = toUserName;
        servers.add(this);
        addOnlineCount();
        String msg = MessageFormat.format("{0} 连接成功，当前在线人数 {1}.", curUserName, onlineCount);
        log.info("建立 webSocket 连接， {}", msg);
        try {
            sendMsg("建立连接后，第一次给客户端发送消息");
        } catch (IOException e) {
            log.error("webSocket error {}.", e);
        }

    }

    // 接受客户端消息
    @OnMessage
    public void onMessage(Session session, String msg) {
        try {
            // 接受到客户端消息，发送给指定接受者
            for (WebSocketServer server : this.servers) {
                if (this.toUserName.equals(server.curUserName)) {
                    String rtMsg = String.valueOf(new Date().getTime());
                    server.sendMsg(MessageFormat.format("{0} ----> {1} ----> {2}", this.curUserName, msg, rtMsg));
                }
            }
        } catch (IOException e) {
            log.error("onMessage error {}", e);
        }
    }

    // 关闭连接
    @OnClose
    public void onClose() {
        servers.remove(this);
        subOnlineCount();
        log.info("{} 关闭连接，当前在线人数 {}.", this.curUserName, this.onlineCount);
    }

    // 连接发生错误时调用
    @OnError
    public void onError(Session session, Throwable e) {
        log.error("web socket 发生错误 {}.", e);
    }

    // 同步方法，记录在线人数
    public synchronized void addOnlineCount() {
        this.onlineCount++;
    }

    public synchronized void subOnlineCount() {
        this.onlineCount--;
    }
}
