package cn.itcast.mq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:  QuorumConfig
 * Description:
 *
 * @author Jay
 * @version v1.0
 */
@Configuration
public class QuorumConfig {

    @Bean
    public Queue quorumQueue() {
        return QueueBuilder.durable("quorum.queue").quorum().build();
    }

}
