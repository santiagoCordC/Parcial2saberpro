package co.edu.unicauca.question.infrastructure.jpa.repository;

import co.edu.unicauca.question.infrastructure.jpa.record.QuestionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringQuestionDataRepository extends JpaRepository<QuestionRecord, Long> {
}
