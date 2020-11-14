package ink.fujisann.learning.base.listener.launcher;

import ink.fujisann.learning.base.utils.mail.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 监听启动失败
 *
 * @author hulei
 * @date 2020-11-14 09:26:9:26
 */
@Component
@Slf4j
public class SpringOnFail implements ApplicationListener<ApplicationFailedEvent> {
    @Override
    public void onApplicationEvent(ApplicationFailedEvent applicationFailedEvent) {
        MailUtil.sendSimpleMailDefault("learning 启动失败", "learning 启动失败");
    }
}
