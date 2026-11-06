package co.edu.unicauca.question.infrastructure.rest.dto;

import co.edu.unicauca.question.domain.model.AnswerOption;
import co.edu.unicauca.question.domain.model.Question;

import java.util.List;

public record QuestionResponse(Long id,
                               String statement,
                               String justification,
                               String status,
                               List<OptionResponse> options) {
    public record OptionResponse(String label, String text, boolean correct) {
        static OptionResponse from(AnswerOption option) {
            return new OptionResponse(option.getLabel(), option.getText(), option.isCorrect());
        }
    }

    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getStatement(),
                question.getJustification(),
                question.getStatus().name(),
                question.getOptions().stream().map(OptionResponse::from).toList());
    }
}
