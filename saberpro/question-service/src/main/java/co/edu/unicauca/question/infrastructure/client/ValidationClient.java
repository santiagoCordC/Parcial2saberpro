package co.edu.unicauca.question.infrastructure.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

// Cliente REST síncrono hacia Validation Service.
@Component
public class ValidationClient {
    private static final Logger log = LoggerFactory.getLogger(ValidationClient.class);

    private final RestTemplate restTemplate;
    private final String validationUrl;

    public ValidationClient(RestTemplate restTemplate,
                            @Value("${validation.service.url}") String validationUrl) {
        this.restTemplate = restTemplate;
        this.validationUrl = validationUrl;
    }

    public ValidationResult validate(String statement, String justification) {
        log.info("[Question] Llamada síncrona POST {}", validationUrl);
        try {
            ValidationResult result = restTemplate.postForObject(
                    validationUrl,
                    new ValidationPayload(statement, justification),
                    ValidationResult.class);
            return result == null
                    ? new ValidationResult(false, "Validation Service devolvió una respuesta vacía")
                    : result;
        } catch (RestClientException e) {
            throw new ValidationServiceUnavailableException(
                    "No fue posible comunicarse con Validation Service en " + validationUrl, e);
        }
    }

    public record ValidationPayload(String statement, String justification) {
    }

    public record ValidationResult(boolean valid, String message) {
    }
}
