package co.edu.unicauca.question.domain;

import co.edu.unicauca.question.domain.builder.QuestionBuilder;
import co.edu.unicauca.question.domain.model.Question;
import co.edu.unicauca.question.domain.state.PendingReviewState;
import co.edu.unicauca.question.domain.state.QuestionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionPatternsTest {
    private QuestionBuilder validBuilder() {
        QuestionBuilder builder = new QuestionBuilder();
        builder.withStatement("¿Cuál es la capital de Colombia?")
                .withJustification("Bogotá es la capital según la Constitución de 1991.")
                .addOption("Medellín")
                .addCorrectOption("Bogotá")
                .addOption("Cali")
                .addOption("Popayán");
        return builder;
    }

    @Test
    void builderCreaPreguntaValidaEnBorrador() {
        Question q = validBuilder().build();

        assertEquals(4, q.getOptions().size());
        assertEquals("B", q.getCorrectOption().getLabel());
        assertEquals("Bogotá", q.getCorrectOption().getText());
        assertEquals(QuestionStatus.DRAFT, q.getStatus());
    }

    @Test
    void builderRechazaMenosDeCuatroOpciones() {
        QuestionBuilder b = new QuestionBuilder();
        b.withStatement("Enunciado").addCorrectOption("A").addOption("B").addOption("C");
        assertThrows(IllegalStateException.class, b::build);
    }

    @Test
    void builderRechazaMasDeCuatroOpciones() {
        QuestionBuilder b = validBuilder();
        b.addOption("Pasto");
        assertThrows(IllegalStateException.class, b::build);
    }

    @Test
    void builderRechazaSinRespuestaCorrecta() {
        QuestionBuilder b = new QuestionBuilder();
        b.withStatement("Enunciado").addOption("A").addOption("B").addOption("C").addOption("D");
        assertThrows(IllegalStateException.class, b::build);
    }

    @Test
    void builderRechazaDosRespuestasCorrectas() {
        QuestionBuilder b = new QuestionBuilder();
        b.withStatement("Enunciado").addCorrectOption("A").addCorrectOption("B").addOption("C").addOption("D");
        assertThrows(IllegalStateException.class, b::build);
    }

    @Test
    void builderRechazaEnunciadoVacio() {
        QuestionBuilder b = new QuestionBuilder();
        b.withStatement("  ").addCorrectOption("A").addOption("B").addOption("C").addOption("D");
        assertThrows(IllegalStateException.class, b::build);
    }

    @Test
    void stateTransitaDeDraftAPendingReview() {
        Question q = validBuilder().build();

        q.submitForReview();

        assertEquals(QuestionStatus.PENDING_REVIEW, q.getStatus());
        assertInstanceOf(PendingReviewState.class, q.getState());
    }

    @Test
    void stateNoPermiteReenviarUnaPreguntaPendiente() {
        Question q = validBuilder().build();
        q.submitForReview();

        assertThrows(IllegalStateException.class, q::submitForReview);
    }

    @Test
    void statusReconstruyeElEstadoSinCondicionales() {
        assertEquals(QuestionStatus.PENDING_REVIEW,
                QuestionStatus.valueOf("PENDING_REVIEW").toState().getStatus());
    }
}
