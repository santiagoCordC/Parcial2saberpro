package co.edu.unicauca.question.domain.repository;

import co.edu.unicauca.question.domain.model.Question;

import java.util.List;
import java.util.Optional;

// Puerto de persistencia del dominio.
public interface IQuestionRepository {
    Question save(Question question);

    Optional<Question> findById(Long id);

    List<Question> findAll();
}
