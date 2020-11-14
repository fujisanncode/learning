package ink.fujisann.learning.base.listener.launcher;

import ink.fujisann.learning.base.utils.mail.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Spring启动成功
 *
 * @author hulei
 * @date 2020-11-14 09:24:9:24
 */
@Component
@Slf4j
public class SpringOnReady implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        MailUtil.sendSimpleMailDefault("learning 启动成功", "learning 启动成功");
    }
}
