package co.edu.unicauca.question.infrastructure.rest;

import co.edu.unicauca.question.application.exception.InvalidQuestionException;
import co.edu.unicauca.question.application.exception.QuestionNotFoundException;
import co.edu.unicauca.question.infrastructure.client.ValidationServiceUnavailableException;
import co.edu.unicauca.question.infrastructure.rest.dto.ErrorResponse;
import org.springframework.amqp.AmqpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Traduce excepciones a códigos HTTP.
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidQuestionException.class)
    public ResponseEntity<ErrorResponse> handleInvalid(InvalidQuestionException e) {
        return build(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException e) {
        return build(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(QuestionNotFoundException e) {
        return build(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ValidationServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleValidationDown(ValidationServiceUnavailableException e) {
        return build(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    }

    @ExceptionHandler(AmqpException.class)
    public ResponseEntity<ErrorResponse> handleAmqp(AmqpException e) {
        return build(HttpStatus.SERVICE_UNAVAILABLE, "No fue posible publicar en RabbitMQ: " + e.getMessage());
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(ErrorResponse.of(status.value(), status.getReasonPhrase(), message));
    }
}
