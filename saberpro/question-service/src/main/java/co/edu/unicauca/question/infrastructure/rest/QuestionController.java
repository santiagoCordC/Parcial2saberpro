package co.edu.unicauca.question.infrastructure.rest;

import co.edu.unicauca.question.application.QuestionService;
import co.edu.unicauca.question.domain.builder.IQuestionBuilder;
import co.edu.unicauca.question.domain.builder.QuestionBuilder;
import co.edu.unicauca.question.domain.model.Question;
import co.edu.unicauca.question.infrastructure.messaging.SubmitQuestionForReview;
import co.edu.unicauca.question.infrastructure.rest.dto.QuestionRequest;
import co.edu.unicauca.question.infrastructure.rest.dto.QuestionResponse;
import co.edu.unicauca.question.infrastructure.rest.dto.SubmitAcceptedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // 201 si es válida, 400 si no (no se guarda).
    @PostMapping
    public ResponseEntity<QuestionResponse> create(@RequestBody QuestionRequest request) {
        Question question = toDomain(request);
        Question saved = questionService.createQuestion(question);
        return ResponseEntity
                .created(URI.create("/questions/" + saved.getId()))
                .body(QuestionResponse.from(saved));
    }

    @GetMapping
    public List<QuestionResponse> findAll() {
        return questionService.findAll().stream().map(QuestionResponse::from).toList();
    }

    @GetMapping("/{id}")
    public QuestionResponse findById(@PathVariable Long id) {
        return QuestionResponse.from(questionService.findById(id));
    }

    // 202 Accepted con operationId.
    @PostMapping("/{id}/submit")
    public ResponseEntity<SubmitAcceptedResponse> submitForReview(@PathVariable Long id) {
        SubmitQuestionForReview command = questionService.submitForReview(id);
        return ResponseEntity.accepted().body(new SubmitAcceptedResponse(
                command.getOperationId(),
                command.getQuestionId(),
                "ACCEPTED",
                "Solicitud de revisión enviada a Lifecycle Service"));
    }

    // Arma la pregunta con el Builder.
    private Question toDomain(QuestionRequest request) {
        IQuestionBuilder builder = new QuestionBuilder()
                .withStatement(request.getStatement())
                .withJustification(request.getJustification());

        for (QuestionRequest.OptionRequest option : request.getOptions()) {
            if (option.isCorrect()) {
                builder.addCorrectOption(option.getText());
            } else {
                builder.addOption(option.getText());
            }
        }
        return builder.build();
    }
}
