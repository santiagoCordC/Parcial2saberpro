package co.edu.unicauca.question.infrastructure.jpa;

import co.edu.unicauca.question.domain.builder.QuestionBuilder;
import co.edu.unicauca.question.domain.model.Question;
import co.edu.unicauca.question.domain.state.QuestionStatus;
import co.edu.unicauca.question.infrastructure.jpa.mapper.QuestionDomainMapper;
import co.edu.unicauca.question.infrastructure.jpa.record.QuestionRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDomainMapperTest {
    private final QuestionDomainMapper mapper = new QuestionDomainMapper();

    private Question sampleQuestion() {
        QuestionBuilder builder = new QuestionBuilder();
        builder.withStatement("¿Cuál es la capital de Colombia?")
                .withJustification("Bogotá es la capital según la Constitución de 1991.")
                .addOption("Medellín")
                .addCorrectOption("Bogotá")
                .addOption("Cali")
                .addOption("Popayán");
        return builder.build();
    }

    @Test
    void dominioARecordConservaDatosYRelacion() {
        Question question = sampleQuestion();
        question.setId(7L);

        QuestionRecord record = mapper.toRecord(question);

        assertEquals(7L, record.getId());
        assertEquals("DRAFT", record.getStatus());
        assertEquals(4, record.getOptions().size());
        assertTrue(record.getOptions().stream().allMatch(o -> o.getQuestion() == record));
        assertEquals(1, record.getOptions().stream().filter(o -> o.isCorrect()).count());
    }

    @Test
    void idaYVueltaConservaEstadoYOpciones() {
        Question original = sampleQuestion();
        original.setId(3L);
        original.submitForReview();

        Question restored = mapper.toDomain(mapper.toRecord(original));

        assertEquals(3L, restored.getId());
        assertEquals(original.getStatement(), restored.getStatement());
        assertEquals(original.getJustification(), restored.getJustification());
        assertEquals(QuestionStatus.PENDING_REVIEW, restored.getStatus());
        assertEquals(original.getOptions(), restored.getOptions());
        assertEquals("B", restored.getCorrectOption().getLabel());
    }
}
