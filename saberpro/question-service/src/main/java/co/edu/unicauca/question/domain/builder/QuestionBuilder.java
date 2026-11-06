package co.edu.unicauca.question.domain.builder;

import co.edu.unicauca.question.domain.model.AnswerOption;
import co.edu.unicauca.question.domain.model.Question;

import java.util.ArrayList;
import java.util.List;

// Builder concreto; las letras A-D se asignan en orden.
public class QuestionBuilder implements IQuestionBuilder {
    public static final int REQUIRED_OPTIONS = 4;
    public static final int REQUIRED_CORRECT_OPTIONS = 1;
    private static final String[] LABELS = {"A", "B", "C", "D"};

    private String statement;
    private String justification;
    private final List<String> optionTexts = new ArrayList<>();
    private final List<Boolean> optionCorrectFlags = new ArrayList<>();

    @Override
    public IQuestionBuilder withStatement(String statement) {
        this.statement = statement;
        return this;
    }

    @Override
    public IQuestionBuilder withJustification(String justification) {
        this.justification = justification;
        return this;
    }

    @Override
    public IQuestionBuilder addOption(String text) {
        return add(text, false);
    }

    @Override
    public IQuestionBuilder addCorrectOption(String text) {
        return add(text, true);
    }

    private IQuestionBuilder add(String text, boolean correct) {
        optionTexts.add(text);
        optionCorrectFlags.add(correct);
        return this;
    }

    @Override
    public Question build() {
        validate();

        List<AnswerOption> options = new ArrayList<>();
        for (int i = 0; i < optionTexts.size(); i++) {
            options.add(new AnswerOption(LABELS[i], optionTexts.get(i).trim(), optionCorrectFlags.get(i)));
        }
        return new Question(statement.trim(), justification == null ? null : justification.trim(), options);
    }

    // Valida 4 opciones y 1 correcta antes de crear la pregunta.
    private void validate() {
        if (isBlank(statement)) {
            throw new IllegalStateException("El enunciado de la pregunta es obligatorio");
        }
        if (optionTexts.size() != REQUIRED_OPTIONS) {
            throw new IllegalStateException("La pregunta debe tener exactamente " + REQUIRED_OPTIONS
                    + " opciones de respuesta (tiene " + optionTexts.size() + ")");
        }
        if (optionTexts.stream().anyMatch(QuestionBuilder::isBlank)) {
            throw new IllegalStateException("Ninguna opción de respuesta puede estar vacía");
        }
        long distinct = optionTexts.stream().map(t -> t.trim().toLowerCase()).distinct().count();
        if (distinct != optionTexts.size()) {
            throw new IllegalStateException("Las opciones de respuesta no pueden repetirse");
        }
        long correctCount = optionCorrectFlags.stream().filter(Boolean::booleanValue).count();
        if (correctCount != REQUIRED_CORRECT_OPTIONS) {
            throw new IllegalStateException("La pregunta debe tener exactamente " + REQUIRED_CORRECT_OPTIONS
                    + " respuesta correcta (tiene " + correctCount + ")");
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
