package cn.itcast.mq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(CommonConfig.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取RabbitTemplate
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        //设置ReturnCallback
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            //判断是否是延迟消息
            if (message.getMessageProperties().getReceivedDelay() > 0){
                //是一个延迟消息,忽略这个消息提示
                return;
            }
            log.error("消息发送失败，交换机：{}，路由key：{}，消息：{}，状态码：{}，原因：{}",
                    exchange, routingKey, message, replyCode, replyText);
        });
        // 重新发送消息或者给管理员通知
    }
}
