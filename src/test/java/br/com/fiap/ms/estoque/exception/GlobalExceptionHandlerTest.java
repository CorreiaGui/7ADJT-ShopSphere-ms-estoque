package br.com.fiap.ms.estoque.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @Test
    void deveTratarSystemBaseException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        EstoqueNaoEncontradoException exception = new EstoqueNaoEncontradoException();

        ResponseEntity<Map<String, Object>> response = handler.handleSystemBaseException(exception);

        assertEquals(422, response.getStatusCodeValue());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(exception.getHttpStatus(), body.get("status"));
        assertEquals(exception.getCode(), body.get("code"));
        assertEquals(exception.getMessage(), body.get("message"));
        assertNotNull(body.get("timestamp")); // apenas garante que foi preenchido
    }
}
