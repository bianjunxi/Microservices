package cn.itcast.mq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
