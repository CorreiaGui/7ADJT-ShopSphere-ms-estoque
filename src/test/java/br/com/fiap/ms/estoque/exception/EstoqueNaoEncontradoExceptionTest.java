package br.com.fiap.ms.estoque.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueNaoEncontradoExceptionTest {

    @Test
    void deveExecutarTodosOsMetodosDaException() {
        EstoqueNaoEncontradoException exception = new EstoqueNaoEncontradoException();

        assertEquals("estoque.estoqueNaoEncontrado", exception.getCode());
        assertEquals("Estoque não encontrado.", exception.getMessage());
        assertEquals(422, exception.getHttpStatus());
    }
}
