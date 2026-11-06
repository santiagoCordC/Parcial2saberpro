package co.edu.unicauca.question.domain.state;

import co.edu.unicauca.question.domain.model.Question;

// Pendiente de revisión: no se puede reenviar.
public class PendingReviewState implements IQuestionState {
    @Override
    public void submitForReview(Question question) {
        throw new IllegalStateException("La pregunta ya se encuentra pendiente de revisión");
    }

    @Override
    public QuestionStatus getStatus() {
        return QuestionStatus.PENDING_REVIEW;
    }
}
