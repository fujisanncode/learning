package ink.fujisann.learning.configure.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicExchangeConfig {

    // 绑定到队列的key
    public final static String manKey = "topic.man";
    public final static String womanKey = "topic.woman";

    // 交换机名称
    public final static String topicExchange = "topic-exchange";

    // bean的名称默认和方法名称相同
    @Bean
    public Queue manQueue() {
        return new Queue(manKey);
    }

    @Bean
    public Queue womanQueue() {
        return new Queue(womanKey);
    }

    @Bean
    public TopicExchange topicExchangeGenerator() {
        return new TopicExchange(topicExchange);
    }

    @Bean
    // 携带man-Key 的消息，通过交换机，发送到manQueue队列
    public Binding bindingToManQueue() {
        return BindingBuilder.bind(manQueue()).to(topicExchangeGenerator()).with(manKey);
    }

    @Bean
    // 携带通用key的消息，通过交换机，发动到womanQueue(即满足匹配的消息都会发送到womanQueue)
    public Binding bindingToWomanQueue() {
        return BindingBuilder.bind(womanQueue()).to(topicExchangeGenerator()).with("topic.#");
    }

}
