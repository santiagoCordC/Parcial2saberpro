package co.edu.unicauca.lifecycle.messaging;

import co.edu.unicauca.lifecycle.service.LifecycleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// Consume question.submit.review.queue.
@Component
public class SubmitQuestionListener {
    private static final Logger log = LoggerFactory.getLogger(SubmitQuestionListener.class);

    private final LifecycleService lifecycleService;

    public SubmitQuestionListener(LifecycleService lifecycleService) {
        this.lifecycleService = lifecycleService;
    }

    @RabbitListener(queues = "${saberpro.amqp.queue}")
    public void onSubmitQuestionForReview(SubmitQuestionForReview command) {
        log.info("[Lifecycle] Mensaje recibido de la cola: {}", command);
        lifecycleService.moveToPendingReview(command);
    }
}
