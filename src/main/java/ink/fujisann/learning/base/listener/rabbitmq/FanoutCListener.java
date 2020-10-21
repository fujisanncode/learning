package ink.fujisann.learning.base.listener.rabbitmq;

import ink.fujisann.learning.base.configure.rabbitmq.FanoutExchangeConfig;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 监听fanout.c队列的消息
@Component
@RabbitListener (queues = FanoutExchangeConfig.fanC)
@Slf4j
public class FanoutCListener {

    @RabbitHandler
    public void receiveFanoutCQueue(Map<String, String> msgMap) {
        log.info("consumer msg from fanout.c queue ===> {}", msgMap.toString());
    }

}
