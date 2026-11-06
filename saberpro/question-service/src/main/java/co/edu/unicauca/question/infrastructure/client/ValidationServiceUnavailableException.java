package co.edu.unicauca.question.infrastructure.client;

public class ValidationServiceUnavailableException extends RuntimeException {
    public ValidationServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
