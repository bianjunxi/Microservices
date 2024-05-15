package cn.itcast.mq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ClassName:  SpringAmqpTest
 * Description:
 *
 * @author Jay
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSimpleQueue() {
        String queue = "simple.queue";
        String message = "hello,spring amqp!";
        rabbitTemplate.convertAndSend(queue, message);
    }

    @Test
    public void testWorkQueue() throws InterruptedException {
        String queue = "simple.queue";
        String message = "hello,spring amqp!";
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queue, message + i);
            Thread.sleep(20);
        }
    }

    @Test
    public void testSendFountExchange(){
        String exchange = "fanout";
        String message = "hello,every one!";
        rabbitTemplate.convertAndSend(exchange,"",message);
    }

    @Test
    public void testSendDirectExchange(){
        String exchange = "direct";
        String message = "hello,red!";
        rabbitTemplate.convertAndSend(exchange,"red",message);
    }

    @Test
    public void testSendTopicExchange(){
        String exchange = "topic";
        String message = "天气很不错!";
        rabbitTemplate.convertAndSend(exchange,"china.weather",message);
    }

    @Test
    public void testSendObjectQueue(){
        Map<String, Object> msg = new HashMap<>();
        msg.put("name","z");
        msg.put("age",20);
        rabbitTemplate.convertAndSend("object.queue",msg);
    }

}
