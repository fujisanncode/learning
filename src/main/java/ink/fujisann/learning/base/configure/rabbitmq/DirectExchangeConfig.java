package ink.fujisann.learning.base.configure.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {

    // 直连交换机名称
    public final static String directExchangeName = "direct exchange";

    public final static String directRouteKey = "directRouting";

    // 直连交换机
    @Bean
    public DirectExchange exchangeConfig() {
        // 交换机名称
        return new DirectExchange("direct exchange");
    }

    // 设置队列名称
    @Bean
    public Queue queueConfig() {
        // 队列持久化
        return new Queue("direct queue", true);
    }

    // 设置交换机和队列的绑定匹配键
    @Bean
    public Binding bindingConfig() {
        return BindingBuilder.bind(queueConfig()).to(exchangeConfig()).with("directRouting");
    }

}
