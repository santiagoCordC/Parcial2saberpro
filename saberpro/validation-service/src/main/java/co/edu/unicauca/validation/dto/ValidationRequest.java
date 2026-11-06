package co.edu.unicauca.validation.dto;

public class ValidationRequest {
    private String statement;
    private String justification;

    public ValidationRequest() {
    }

    public ValidationRequest(String statement, String justification) {
        this.statement = statement;
        this.justification = justification;
    }

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
}
