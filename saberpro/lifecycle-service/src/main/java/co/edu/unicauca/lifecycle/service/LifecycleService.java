package co.edu.unicauca.lifecycle.service;

import co.edu.unicauca.lifecycle.messaging.SubmitQuestionForReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// Aplica la transición a PENDING_REVIEW.
@Service
public class LifecycleService {
    private static final Logger log = LoggerFactory.getLogger(LifecycleService.class);
    public static final String PENDING_REVIEW = "PENDING_REVIEW";

    private final Map<Long, QuestionLifecycle> lifecycleByQuestion = new ConcurrentHashMap<>();

    public QuestionLifecycle moveToPendingReview(SubmitQuestionForReview command) {
        QuestionLifecycle transition = new QuestionLifecycle(
                command.getQuestionId(),
                command.getOperationId(),
                command.getCurrentStatus(),
                PENDING_REVIEW,
                Instant.now().toString());

        lifecycleByQuestion.put(command.getQuestionId(), transition);

        log.info("[Lifecycle] operationId={} | Pregunta {}: {} -> {}",
                transition.operationId(), transition.questionId(),
                transition.previousStatus(), transition.status());
        return transition;
    }

    public Optional<QuestionLifecycle> findByQuestionId(Long questionId) {
        return Optional.ofNullable(lifecycleByQuestion.get(questionId));
    }

    public List<QuestionLifecycle> findAll() {
        return new ArrayList<>(lifecycleByQuestion.values());
    }

    public record QuestionLifecycle(Long questionId,
                                    String operationId,
                                    String previousStatus,
                                    String status,
                                    String updatedAt) {
    }
}
