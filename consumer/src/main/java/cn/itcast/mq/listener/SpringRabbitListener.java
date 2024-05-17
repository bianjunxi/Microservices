package cn.itcast.mq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SpringRabbitListener {

    private static final Logger log = LoggerFactory.getLogger(SpringRabbitListener.class);

    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String msg) {
        //debug：方便查看消息重试对应的时间，
        //重试次数超过4次，会Spring会返回reject拒接，rabbitmq丢弃消息
        //丢弃消息可以接受，因为本次重试多次依然不能成功，即便返回nack，重新投递本地未必可以成功
        log.debug("消费者接收到simple.queue的消息：【" + msg + "】");
        System.out.println(1/0);
        log.info("消费者消息处理成功！");
    }

    //死信交换机 + TTL实现延迟消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "dl.queue",durable = "true"),
            exchange = @Exchange(name = "dl.direct"),
            key = "dl"
    ))
    public void listenDlQueue(String msg){
        log.info("消费者接收到了dl.queue的延迟消息");
    }

    //使用死信插件实现延迟消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "delay.queue",durable = "true"),
            exchange = @Exchange(name = "delay.direct",delayed = "true"),
            key = "delay"
    ))
    public void listenDelayExchange(String msg){
        log.info("消费者接收到了delay.queue的延迟消息");
    }

}
