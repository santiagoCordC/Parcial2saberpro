package co.edu.unicauca.question.domain.builder;

import co.edu.unicauca.question.domain.model.Question;

// Patrón Builder: construcción fluida de la pregunta.
public interface IQuestionBuilder {
    IQuestionBuilder withStatement(String statement);

    IQuestionBuilder withJustification(String justification);

    IQuestionBuilder addOption(String text);

    IQuestionBuilder addCorrectOption(String text);

    Question build();
}
