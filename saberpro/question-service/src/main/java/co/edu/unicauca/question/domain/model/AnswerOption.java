package co.edu.unicauca.question.domain.model;

import java.util.Objects;

// Opción de respuesta (A-D) de una pregunta.
public class AnswerOption {
    private final String label;
    private final String text;
    private final boolean correct;

    public AnswerOption(String label, String text, boolean correct) {
        this.label = label;
        this.text = text;
        this.correct = correct;
    }

    public String getLabel() {
        return label;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnswerOption)) return false;
        AnswerOption that = (AnswerOption) o;
        return correct == that.correct
                && Objects.equals(label, that.label)
                && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, text, correct);
    }

    @Override
    public String toString() {
        return label + ") " + text + (correct ? " [CORRECTA]" : "");
    }
}
