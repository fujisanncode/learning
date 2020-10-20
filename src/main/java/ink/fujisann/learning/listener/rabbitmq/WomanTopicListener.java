package ink.fujisann.learning.listener.rabbitmq;

import ink.fujisann.learning.configure.rabbitmq.TopicExchangeConfig;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 监听topic.woman队列的消息
@Component
@RabbitListener (queues = TopicExchangeConfig.womanKey)
@Slf4j
public class WomanTopicListener {

    @RabbitHandler
    public void receiveWomanQueue(Map<String, String> msgMap) {
        log.info("consumer msg from woman queue ===> {}", msgMap.toString());
    }

}
