package top.lucas9.lblog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lucas9.lblog.common.Constant;

/**
 * @author lucas
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue exQueue() {
        return new Queue(Constant.MQ_QUEUE);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(Constant.MQ_EXCHANGE);
    }

    @Bean
    Binding binding(Queue exQueue, DirectExchange exchange) {
        return BindingBuilder.bind(exQueue).to(exchange).with(Constant.MQ_BIND_KEY);
    }

}
