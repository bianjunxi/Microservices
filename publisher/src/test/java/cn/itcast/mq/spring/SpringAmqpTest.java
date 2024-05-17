package cn.itcast.mq.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue() throws InterruptedException {
        // 1.准备消息
        String routingKey = "simple";
        String message = "hello, spring amqp!";

        // 2.准备CorrelationData
        // 2.1.消息ID，需要封装到CorrelationData中
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 2.2.添加CallBack
        // getFuture：获取将来的对象,在将来的某一刻拿到回调
        correlationData.getFuture().addCallback(result -> {
                    // 判断结果
                    if (result.isAck()) {
                        //ack消息成功
                        log.info("消息发送成功，ID：{}", correlationData.getId());
                    } else {
                        //nack消息失败
                        log.error("消息发送失败，ID：{}，原因：{}", correlationData.getId(), result.getReason());
                    }
                },
                ex -> {
                    // 记录日志
                    log.error("消息发送异常，ID：{}，原因：{}", correlationData.getId(), ex.getMessage());
                    // 重发消息
                });

        // 3.发送消息
        // 模拟问题
        // ① 消息没有到达交换机：网络丢包、交换机名称写错
        // ② 消息没有到达队列：交换机没投递到队列服务器故障、队列名称写错
        rabbitTemplate.convertAndSend("amq.topic", routingKey, message, correlationData);
    }

    //消息持久化
    @Test
    public void testDurableMessage() throws InterruptedException {
        //1.准备消息
        Message message = MessageBuilder.withBody("hello, spring amqp!".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        //2.发送消息
        rabbitTemplate.convertAndSend("simple.queue",message);
        // 3.测试发送消息后，不开启消费者，重启服务查看控制面板消息是否丢失
    }

    //死信交换机 + TTL 延迟消息
    @Test
    public void testTTLMessage() throws InterruptedException {
        //1.准备消息
        Message message = MessageBuilder
                .withBody("hello, spring ttl message!".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setExpiration("5000")
                .build();
        //2.发送消息
        rabbitTemplate.convertAndSend("ttl.direct","ttl",message);
    }

    //死信插件实现延迟消息
    @Test
    public void testSendDelayMessage() throws InterruptedException {
        //1.准备消息
        Message message = MessageBuilder
                .withBody("hello, spring ttl message!".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setHeader("x-delay",5000)
                .build();
        //2.发送消息
        rabbitTemplate.convertAndSend("delay.direct","delay",message);
    }

}
