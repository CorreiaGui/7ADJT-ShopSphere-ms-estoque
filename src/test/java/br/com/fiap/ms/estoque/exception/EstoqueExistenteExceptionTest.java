package br.com.fiap.ms.estoque.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueExistenteExceptionTest {

    @Test
    void deveExecutarTodosOsMetodosDaException() {
        EstoqueExistenteException exception = new EstoqueExistenteException();

        assertEquals("estoque.estoqueJaExiste", exception.getCode());
        assertEquals("Estoque já cadastrado.", exception.getMessage());
        assertEquals(422, exception.getHttpStatus());
    }
}

