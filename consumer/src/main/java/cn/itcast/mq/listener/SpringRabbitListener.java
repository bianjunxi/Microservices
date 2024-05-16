package cn.itcast.mq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SpringRabbitListener {

    private static final Logger log = LoggerFactory.getLogger(SpringRabbitListener.class);

    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String msg) {
        System.out.println("消费者接收到simple.queue的消息：【" + msg + "】");
        System.out.println(1/0);
        log.info("消费者消息处理成功！");
    }
}
