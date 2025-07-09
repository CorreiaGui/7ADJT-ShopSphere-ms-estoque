package br.com.fiap.ms.estoque.controller.json;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EstoqueJsonTest {

    @Test
    void deveCriarEstoqueJsonComValoresCorretos() {
        UUID id = UUID.randomUUID();
        String sku = "SKU123";
        Long quantidade = 15L;
        LocalDateTime dataCriacao = LocalDateTime.now().minusDays(1);
        LocalDateTime dataUltimaAlteracao = LocalDateTime.now();

        EstoqueJson estoqueJson = new EstoqueJson(id, sku, quantidade, dataCriacao, dataUltimaAlteracao);

        assertEquals(id, estoqueJson.id());
        assertEquals(sku, estoqueJson.sku());
        assertEquals(quantidade, estoqueJson.quantidade());
        assertEquals(dataCriacao, estoqueJson.dataCriacao());
        assertEquals(dataUltimaAlteracao, estoqueJson.dataUltimaAlteracao());
    }

    @Test
    void deveTerEqualsEHashCodeFuncionando() {
        UUID id = UUID.randomUUID();
        EstoqueJson e1 = new EstoqueJson(id, "SKU1", 10L, LocalDateTime.now(), LocalDateTime.now());
        EstoqueJson e2 = new EstoqueJson(id, "SKU1", 10L, e1.dataCriacao(), e1.dataUltimaAlteracao());

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void toStringDeveConterValores() {
        EstoqueJson e = new EstoqueJson(UUID.randomUUID(), "SKUX", 20L, LocalDateTime.now(), LocalDateTime.now());

        String str = e.toString();

        assertTrue(str.contains("SKUX"));
        assertTrue(str.contains("20"));
    }

}
