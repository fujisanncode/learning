package com.example.sqllearning.listener.rabbitmq;

import com.example.sqllearning.configure.rabbitmq.FanoutExchangeConfig;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 监听fanout.b队列的消息
@Component
@RabbitListener (queues = FanoutExchangeConfig.fanB)
@Slf4j
public class FanoutBListener {

    @RabbitHandler
    public void receiveFanoutBQueue(Map<String, String> msgMap) {
        log.info("consumer msg from fanout.b queue ===> {}", msgMap.toString());
    }

}
