package co.edu.unicauca.validation.controller;

import co.edu.unicauca.validation.dto.ValidationRequest;
import co.edu.unicauca.validation.dto.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Válida si la justificación tiene más de 15 caracteres.
@RestController
public class ValidationController {
    private static final Logger log = LoggerFactory.getLogger(ValidationController.class);
    public static final int MIN_JUSTIFICATION_LENGTH = 15;

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validate(@RequestBody ValidationRequest request) {
        String justification = request.getJustification() == null ? "" : request.getJustification().trim();
        boolean valid = justification.length() > MIN_JUSTIFICATION_LENGTH;

        String message = valid
                ? "Justificación válida"
                : "La justificación debe tener más de " + MIN_JUSTIFICATION_LENGTH
                  + " caracteres (tiene " + justification.length() + ")";

        log.info("[Validation] POST /validate -> valid={} ({} caracteres)", valid, justification.length());
        return ResponseEntity.ok(new ValidationResponse(valid, message));
    }
}
