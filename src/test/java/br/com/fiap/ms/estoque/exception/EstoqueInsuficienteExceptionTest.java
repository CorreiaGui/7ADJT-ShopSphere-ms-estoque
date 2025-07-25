package br.com.fiap.ms.estoque.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueInsuficienteExceptionTest {

    @Test
    void deveExecutarTodosOsMetodosDaException() {
        EstoqueInsuficienteException exception = new EstoqueInsuficienteException();

        assertEquals("estoque.estoqueInsuficiente", exception.getCode());
        assertEquals("Estoque insuficiente.", exception.getMessage());
        assertEquals(400, exception.getHttpStatus());
    }
}
