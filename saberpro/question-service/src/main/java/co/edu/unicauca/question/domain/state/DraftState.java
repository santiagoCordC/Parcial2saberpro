package co.edu.unicauca.question.domain.state;

import co.edu.unicauca.question.domain.model.Question;

// Borrador: puede enviarse a revisión.
public class DraftState implements IQuestionState {
    @Override
    public void submitForReview(Question question) {
        // Draft -> PendingReview
        question.changeState(new PendingReviewState());
    }

    @Override
    public QuestionStatus getStatus() {
        return QuestionStatus.DRAFT;
    }
}
