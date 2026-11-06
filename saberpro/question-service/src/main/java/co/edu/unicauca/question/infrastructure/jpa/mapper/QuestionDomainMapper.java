package co.edu.unicauca.question.infrastructure.jpa.mapper;

import co.edu.unicauca.question.domain.model.AnswerOption;
import co.edu.unicauca.question.domain.model.Question;
import co.edu.unicauca.question.domain.state.QuestionStatus;
import co.edu.unicauca.question.infrastructure.jpa.record.AnswerOptionRecord;
import co.edu.unicauca.question.infrastructure.jpa.record.QuestionRecord;
import org.springframework.stereotype.Component;

import java.util.List;

// Mapper bidireccional Question <-> QuestionRecord.
@Component
public class QuestionDomainMapper {
    public QuestionRecord toRecord(Question question) {
        QuestionRecord record = new QuestionRecord();
        record.setId(question.getId());
        record.setStatement(question.getStatement());
        record.setJustification(question.getJustification());
        record.setStatus(question.getStatus().name());

        question.getOptions().forEach(option ->
                record.addOption(new AnswerOptionRecord(option.getLabel(), option.getText(), option.isCorrect())));
        return record;
    }

    public Question toDomain(QuestionRecord record) {
        List<AnswerOption> options = record.getOptions().stream()
                .map(o -> new AnswerOption(o.getLabel(), o.getText(), o.isCorrect()))
                .toList();

        return new Question(
                record.getId(),
                record.getStatement(),
                record.getJustification(),
                options,
                QuestionStatus.valueOf(record.getStatus()).toState());
    }
}
