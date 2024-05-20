package cn.itcast.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消费者启动的时候就会创建队列和交换机
 */
//@Configuration
public class CommonConfig {
    //交换机持久化
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("simple.exchange",true,false);
    }
    //队列持久化
    @Bean
    public Queue queue(){
        return QueueBuilder.durable("simple.queue").build();
    }
}
