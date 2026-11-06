package co.edu.unicauca.question.infrastructure.jpa.repository;

import co.edu.unicauca.question.domain.model.Question;
import co.edu.unicauca.question.domain.repository.IQuestionRepository;
import co.edu.unicauca.question.infrastructure.jpa.mapper.QuestionDomainMapper;
import co.edu.unicauca.question.infrastructure.jpa.record.QuestionRecord;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Implementación JPA (H2) del repositorio del dominio.
@Repository
public class QuestionRepositoryJpaImpl implements IQuestionRepository {
    private final SpringQuestionDataRepository springDataRepository;
    private final QuestionDomainMapper mapper;

    public QuestionRepositoryJpaImpl(SpringQuestionDataRepository springDataRepository,
                                     QuestionDomainMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Question save(Question question) {
        QuestionRecord saved = springDataRepository.save(mapper.toRecord(question));
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Question> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findAll() {
        return springDataRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
