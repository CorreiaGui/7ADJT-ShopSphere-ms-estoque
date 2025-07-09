package br.com.fiap.ms.estoque.controller.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EstoqueRequestJsonTest {

    @Test
    void deveCriarEstoqueRequestJsonComValoresCorretos() {
        String sku = "SKU123";
        Long quantidade = 20L;

        EstoqueRequestJson request = new EstoqueRequestJson(sku, quantidade);

        assertEquals(sku, request.sku());
        assertEquals(quantidade, request.quantidade());
    }

    @Test
    void equalsEhConsistente() {
        EstoqueRequestJson r1 = new EstoqueRequestJson("SKU123", 20L);
        EstoqueRequestJson r2 = new EstoqueRequestJson("SKU123", 20L);
        EstoqueRequestJson r3 = new EstoqueRequestJson("SKU999", 10L);

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
    }

    @Test
    void hashCodeEhConsistente() {
        EstoqueRequestJson r1 = new EstoqueRequestJson("SKU123", 20L);
        EstoqueRequestJson r2 = new EstoqueRequestJson("SKU123", 20L);

        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void toStringContemValores() {
        EstoqueRequestJson r = new EstoqueRequestJson("SKU123", 20L);
        String str = r.toString();

        assertTrue(str.contains("SKU123"));
        assertTrue(str.contains("20"));
    }

}
