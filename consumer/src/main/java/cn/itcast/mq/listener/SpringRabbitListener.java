package cn.itcast.mq.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * ClassName:  SpringRabbitListener
 * Description:
 *
 * @author Jay
 * @version v1.0
 */
@Component
public class SpringRabbitListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //基础消息
//    @RabbitListener(queues = "simple.queue")
//    public void listenSimpleQueueMessage(String msg) {
//        System.out.println("spring接收到的消息是：" + msg);
//    }

    // 工作队列模式

    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1接收到的消息是：" + msg +"," + LocalTime.now());
        Thread.sleep(20);
    }

    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue2(String msg) throws InterruptedException {
        System.err.println("消费者2接收到的消息是：" + msg +"," + LocalTime.now());
        Thread.sleep(200);
    }

    // 发布订阅模式
    @RabbitListener(queues = "fount.queue1")
    public void listenFountQueue1(String msg) {
        System.out.println("fount1接收到的消息是：" + msg);
    }

    @RabbitListener(queues = "fount.queue2")
    public void listenFountQueue2(String msg) {
        System.out.println("fount2接收到的消息是：" + msg);
    }

    // 路由模式
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "direct",type = ExchangeTypes.DIRECT),
            key = {"red","blue"}
    ))
    public void listenDirectQueue1(String msg){
        System.out.println("DirectQueue1接收到的消息是：" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "direct",type = ExchangeTypes.DIRECT),
            key = {"red","yellow"}
    ))
    public void listenDirectQueue2(String msg){
        System.out.println("DirectQueue2接收到的消息是：" + msg);
    }


}
