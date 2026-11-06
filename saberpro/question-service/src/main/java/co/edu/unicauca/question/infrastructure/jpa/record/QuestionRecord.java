package co.edu.unicauca.question.infrastructure.jpa.record;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

// Entidad JPA de la pregunta.
@Entity
@Table(name = "questions")
public class QuestionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "statement", nullable = false, length = 1000)
    private String statement;

    @Column(name = "justification", length = 2000)
    private String justification;

    // DRAFT o PENDING_REVIEW
    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("label ASC")
    private List<AnswerOptionRecord> options = new ArrayList<>();

    public QuestionRecord() {
    }

    // Mantiene la relación en ambos sentidos.
    public void addOption(AnswerOptionRecord option) {
        option.setQuestion(this);
        options.add(option);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AnswerOptionRecord> getOptions() {
        return options;
    }

    public void setOptions(List<AnswerOptionRecord> options) {
        this.options = options;
    }
}
