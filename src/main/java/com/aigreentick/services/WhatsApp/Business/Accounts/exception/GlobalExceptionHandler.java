package com.aigreentick.services.WhatsApp.Business.Accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<?> handleWebClientError(WebClientResponseException ex) {

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(Map.of(
                        "timestamp", LocalDateTime.now().toString(),
                        "status", ex.getStatusCode().value(),
                        "error", ex.getMessage(),
                        "response", ex.getResponseBodyAsString()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralError(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "timestamp", LocalDateTime.now().toString(),
                        "error", ex.getMessage()
                ));
    }
}
