package co.edu.unicauca.question.infrastructure.messaging;

// Comando AMQP enviado a Lifecycle Service.
public class SubmitQuestionForReview {
    private String operationId;
    private Long questionId;
    private String currentStatus;
    private String requestedAt;

    public SubmitQuestionForReview() {
    }

    public SubmitQuestionForReview(String operationId, Long questionId, String currentStatus, String requestedAt) {
        this.operationId = operationId;
        this.questionId = questionId;
        this.currentStatus = currentStatus;
        this.requestedAt = requestedAt;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(String requestedAt) {
        this.requestedAt = requestedAt;
    }

    @Override
    public String toString() {
        return "SubmitQuestionForReview{operationId='" + operationId + "', questionId=" + questionId
                + ", currentStatus='" + currentStatus + "', requestedAt='" + requestedAt + "'}";
    }
}
