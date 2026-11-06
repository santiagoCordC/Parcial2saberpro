package co.edu.unicauca.question.application;

import co.edu.unicauca.question.application.exception.InvalidQuestionException;
import co.edu.unicauca.question.application.exception.QuestionNotFoundException;
import co.edu.unicauca.question.domain.model.Question;
import co.edu.unicauca.question.domain.repository.IQuestionRepository;
import co.edu.unicauca.question.infrastructure.client.ValidationClient;
import co.edu.unicauca.question.infrastructure.messaging.QuestionCommandPublisher;
import co.edu.unicauca.question.infrastructure.messaging.SubmitQuestionForReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {
    private static final Logger log = LoggerFactory.getLogger(QuestionService.class);

    private final IQuestionRepository questionRepository;
    private final ValidationClient validationClient;
    private final QuestionCommandPublisher commandPublisher;

    public QuestionService(IQuestionRepository questionRepository,
                           ValidationClient validationClient,
                           QuestionCommandPublisher commandPublisher) {
        this.questionRepository = questionRepository;
        this.validationClient = validationClient;
        this.commandPublisher = commandPublisher;
    }

    // 2.1 Valida por REST y guarda en DRAFT.
    public Question createQuestion(Question question) {
        ValidationClient.ValidationResult result =
                validationClient.validate(question.getStatement(), question.getJustification());

        if (!result.valid()) {
            log.warn("[Question] Validación rechazada: {}", result.message());
            throw new InvalidQuestionException(result.message());
        }

        Question saved = questionRepository.save(question);
        log.info("[Question] Pregunta {} guardada en estado {}", saved.getId(), saved.getStatus());
        return saved;
    }

    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    // 2.2 Publica el comando en RabbitMQ (asíncrono).
    public SubmitQuestionForReview submitForReview(Long id) {
        Question question = findById(id);

        SubmitQuestionForReview command = new SubmitQuestionForReview(
                UUID.randomUUID().toString(),
                question.getId(),
                question.getStatus().name(),
                Instant.now().toString());

        commandPublisher.publishSubmitForReview(command);
        return command;
    }
}
