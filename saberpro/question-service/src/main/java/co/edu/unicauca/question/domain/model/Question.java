package co.edu.unicauca.question.domain.model;

import co.edu.unicauca.question.domain.state.DraftState;
import co.edu.unicauca.question.domain.state.IQuestionState;
import co.edu.unicauca.question.domain.state.QuestionStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Pregunta de dominio (Java puro). Contexto del patrón State.
public class Question {
    private Long id;
    private final String statement;
    private final String justification;
    private final List<AnswerOption> options;
    private IQuestionState state;

    public Question(String statement, String justification, List<AnswerOption> options) {
        this(null, statement, justification, options, new DraftState());
    }

    // Usado por el mapper para reconstruir desde la BD.
    public Question(Long id, String statement, String justification,
                    List<AnswerOption> options, IQuestionState state) {
        this.id = id;
        this.statement = statement;
        this.justification = justification;
        this.options = new ArrayList<>(options);
        this.state = state;
    }

    // Delega en el estado actual, sin condicionales.
    public void submitForReview() {
        state.submitForReview(this);
    }

    public void changeState(IQuestionState newState) {
        this.state = newState;
    }

    public IQuestionState getState() {
        return state;
    }

    public QuestionStatus getStatus() {
        return state.getStatus();
    }

    public AnswerOption getCorrectOption() {
        return options.stream()
                .filter(AnswerOption::isCorrect)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("La pregunta no tiene respuesta correcta"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public String getJustification() {
        return justification;
    }

    public List<AnswerOption> getOptions() {
        return Collections.unmodifiableList(options);
    }

    @Override
    public String toString() {
        return "Question{id=" + id
                + ", statement='" + statement + '\''
                + ", status=" + getStatus()
                + ", options=" + options + '}';
    }
}
