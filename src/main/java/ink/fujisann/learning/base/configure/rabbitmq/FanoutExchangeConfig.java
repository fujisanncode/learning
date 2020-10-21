package ink.fujisann.learning.base.configure.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 扇形交换机，扇形交换机无视消息的路由键值，消息推送到所有的绑定队列中
@Configuration
public class FanoutExchangeConfig {

    // 队列名称
    public final static String fanA = "fan.a";
    public final static String fanB = "fan.b";
    public final static String fanC = "fan.c";

    // 交换机名称
    public final static String fanoutExchange = "fanout-exchange";

    // bean的名称默认和方法名称相同
    @Bean
    public Queue fanAQueue() {
        return new Queue(fanA);
    }

    @Bean
    public Queue fanBQueue() {
        return new Queue(fanB);
    }

    @Bean
    public Queue fanCQueue() {
        return new Queue(fanC);
    }

    @Bean
    public FanoutExchange fanoutExchangeGenerator() {
        return new FanoutExchange(fanoutExchange);
    }

    // 队列绑定当扇形交换机，不能指定交换机的路由键值
    @Bean
    public Binding bindingToFanAQueue() {
        return BindingBuilder.bind(fanAQueue()).to(fanoutExchangeGenerator());
    }

    @Bean
    public Binding bindingToFanBQueue() {
        return BindingBuilder.bind(fanBQueue()).to(fanoutExchangeGenerator());
    }

    @Bean
    public Binding bindingToFanCQueue() {
        return BindingBuilder.bind(fanCQueue()).to(fanoutExchangeGenerator());
    }


}
