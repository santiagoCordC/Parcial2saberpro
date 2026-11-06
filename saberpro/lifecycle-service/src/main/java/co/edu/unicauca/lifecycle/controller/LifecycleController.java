package co.edu.unicauca.lifecycle.controller;

import co.edu.unicauca.lifecycle.service.LifecycleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Consulta del estado (para verificar en Postman).
@RestController
@RequestMapping("/lifecycle/questions")
public class LifecycleController {
    private final LifecycleService lifecycleService;

    public LifecycleController(LifecycleService lifecycleService) {
        this.lifecycleService = lifecycleService;
    }

    @GetMapping
    public List<LifecycleService.QuestionLifecycle> findAll() {
        return lifecycleService.findAll();
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<LifecycleService.QuestionLifecycle> findByQuestionId(@PathVariable Long questionId) {
        return lifecycleService.findByQuestionId(questionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
