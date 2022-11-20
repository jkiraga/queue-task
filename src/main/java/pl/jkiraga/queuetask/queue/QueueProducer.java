package pl.jkiraga.queuetask.queue;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import pl.jkiraga.queuetask.configuration.MessagingRabbitConfiguration;

import javax.transaction.Transactional;

@AllArgsConstructor
@Slf4j
@Component
public class QueueProducer {

    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void addToQueue(Integer resourceId) {
        log.info("Resource {} added to processing queue", resourceId);
        rabbitTemplate.convertAndSend(
                MessagingRabbitConfiguration.topicExchangeName,
                MessagingRabbitConfiguration.routingKey,
                resourceId);
    }
}
