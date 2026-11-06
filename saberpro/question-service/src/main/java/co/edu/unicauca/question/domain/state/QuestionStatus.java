package co.edu.unicauca.question.domain.state;

import java.util.function.Supplier;

// Crea el estado a partir de su nombre, sin if/switch.
public enum QuestionStatus {
    DRAFT(DraftState::new),
    PENDING_REVIEW(PendingReviewState::new);

    private final Supplier<IQuestionState> factory;

    QuestionStatus(Supplier<IQuestionState> factory) {
        this.factory = factory;
    }

    public IQuestionState toState() {
        return factory.get();
    }
}
