package com.example.sqllearning.listener.rabbitmq;

import com.example.sqllearning.configure.rabbitmq.TopicExchangeConfig;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 监听topic.man队列的消息
@Component
@RabbitListener (queues = TopicExchangeConfig.manKey)
@Slf4j
public class ManTopicListener {

    @RabbitHandler
    public void receiveManQueue(Map<String, String> msgMap) {
        log.info("consumer msg from man queue ===> {}", msgMap.toString());
    }

}
