package br.com.fiap.ms.estoque.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemBaseException.class)
    public ResponseEntity<Map<String, Object>> handleSystemBaseException(SystemBaseException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", ZonedDateTime.now());
        response.put("status", ex.getHttpStatus());
        response.put("code", ex.getCode());
        response.put("message", ex.getMessage());

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(response);
    }

}
