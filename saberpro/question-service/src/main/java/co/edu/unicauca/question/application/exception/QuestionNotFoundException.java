package co.edu.unicauca.question.application.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super("No existe la pregunta con id " + id);
    }
}
