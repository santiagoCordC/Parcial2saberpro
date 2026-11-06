package co.edu.unicauca.question.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// Publica comandos en RabbitMQ.
@Component
public class QuestionCommandPublisher {
    private static final Logger log = LoggerFactory.getLogger(QuestionCommandPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    public QuestionCommandPublisher(RabbitTemplate rabbitTemplate,
                                    @Value("${saberpro.amqp.exchange}") String exchangeName,
                                    @Value("${saberpro.amqp.routing-key}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    public void publishSubmitForReview(SubmitQuestionForReview command) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, command);
        log.info("[Question] Publicado en '{}' con routing key '{}': {}", exchangeName, routingKey, command);
    }
}
