package ink.fujisann.learning.base.listener.launcher;

import ink.fujisann.learning.base.utils.mail.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
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
    private ApplicationEventPublisher publisher;

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // 发布实践
        publisher.publishEvent(new SpringOnReadyEvent("测试spring监听"));
    }
}
