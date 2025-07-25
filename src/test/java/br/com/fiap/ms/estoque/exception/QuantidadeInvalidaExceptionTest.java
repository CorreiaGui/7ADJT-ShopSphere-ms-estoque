package br.com.fiap.ms.estoque.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantidadeInvalidaExceptionTest {

    @Test
    void deveExecutarTodosOsMetodosDaException() {
        QuantidadeInvalidaException exception = new QuantidadeInvalidaException();

        assertEquals("estoque.quantidadeErrada", exception.getCode());
        assertEquals("Quantidade deve ser maior que zero.", exception.getMessage());
        assertEquals(400, exception.getHttpStatus());
    }
}
