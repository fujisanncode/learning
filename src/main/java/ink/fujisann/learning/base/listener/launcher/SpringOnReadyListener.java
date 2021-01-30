package ink.fujisann.learning.base.listener.launcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringOnReadyListener {

    /**
     * 配置监听的时间，配置监听器的顺序
     *
     * @param event
     */
    @Order(1)
    @EventListener(SpringOnReadyEvent.class)
    public void sendMail(SpringOnReadyEvent event) {
        log.info("spring 启动后发送邮件");
        //MailUtil.sendSimpleMailDefault("learning 启动成功", "learning 启动成功");
    }

    @Order(2)
    @EventListener(SpringOnReadyEvent.class)
    public void addLog(SpringOnReadyEvent event) {
        log.info("spring 启动后记录日志");
    }

}
