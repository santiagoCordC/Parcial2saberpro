package co.edu.unicauca.question.infrastructure.rest.dto;

public record SubmitAcceptedResponse(String operationId,
                                     Long questionId,
                                     String status,
                                     String message) {
}
