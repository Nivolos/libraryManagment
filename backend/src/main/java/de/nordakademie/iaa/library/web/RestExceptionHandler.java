package de.nordakademie.iaa.library.web;

import de.nordakademie.iaa.library.service.ResourceNotFoundException;
import de.nordakademie.iaa.library.service.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(buildPayload(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Map<String, Object>> handleServiceException(ServiceException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(buildPayload(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    private Map<String, Object> buildPayload(HttpStatus status, String message) {
        return Map.of(
            "timestamp", Instant.now().toString(),
            "status", status.value(),
            "error", status.getReasonPhrase(),
            "message", message
        );
    }
}
