package br.com.ronaldo.market_intelligence.application;

import br.com.ronaldo.market_intelligence.domain.exception.ExternalApiException;
import br.com.ronaldo.market_intelligence.domain.exception.UserExistsException;
import br.com.ronaldo.market_intelligence.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<?> handleUserExists(UserExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<?> handleExternalApi(ExternalApiException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno: " + ex.getMessage());
    }
}
