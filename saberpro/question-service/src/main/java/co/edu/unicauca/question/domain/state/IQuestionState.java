package co.edu.unicauca.question.domain.state;

import co.edu.unicauca.question.domain.model.Question;

// Patrón State: cada estado decide su transición.
public interface IQuestionState {
    void submitForReview(Question question);

    QuestionStatus getStatus();
}
