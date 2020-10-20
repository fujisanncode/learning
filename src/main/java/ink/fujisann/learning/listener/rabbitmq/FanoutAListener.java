package ink.fujisann.learning.listener.rabbitmq;

import ink.fujisann.learning.configure.rabbitmq.FanoutExchangeConfig;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 监听fanout.a队列的消息
@Component
@RabbitListener (queues = FanoutExchangeConfig.fanA)
@Slf4j
public class FanoutAListener {

    @RabbitHandler
    public void receiveFanoutAQueue(Map<String, String> msgMap) {
        log.info("consumer msg from fanout.a queue ===> {}", msgMap.toString());
    }

}
