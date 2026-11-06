package co.edu.unicauca.question.infrastructure.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class QuestionRequest {
    private String statement;
    private String justification;
    private List<OptionRequest> options = new ArrayList<>();

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public List<OptionRequest> getOptions() {
        return options;
    }

    public void setOptions(List<OptionRequest> options) {
        this.options = options == null ? new ArrayList<>() : options;
    }

    public static class OptionRequest {
        private String text;
        private boolean correct;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isCorrect() {
            return correct;
        }

        public void setCorrect(boolean correct) {
            this.correct = correct;
        }
    }
}
