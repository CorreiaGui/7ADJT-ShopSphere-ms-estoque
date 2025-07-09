package br.com.fiap.ms.estoque.utils;

import br.com.fiap.ms.estoque.controller.json.EstoqueJson;
import br.com.fiap.ms.estoque.controller.json.EstoqueRequestJson;
import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.gateway.database.jpa.entity.EstoqueEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EstoqueUtilsTest {

    @Test
    void deveConverterEstoqueEntityParaEstoque() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        EstoqueEntity entity = new EstoqueEntity(id, "SKU123", 10L, now, now);

        Estoque estoque = EstoqueUtils.convertToEstoque(entity);

        assertEquals(id, estoque.getId());
        assertEquals("SKU123", estoque.getSku());
        assertEquals(10L, estoque.getQuantidade());
        assertEquals(now, estoque.getDataCriacao());
        assertEquals(now, estoque.getDataUltimaAlteracao());
    }

    @Test
    void deveConverterEstoqueParaEstoqueJson() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Estoque estoque = new Estoque(id, "SKU456", 20L, now, now);

        EstoqueJson json = EstoqueUtils.convertToEstoqueJson(estoque);

        assertEquals(id, json.id());
        assertEquals("SKU456", json.sku());
        assertEquals(20L, json.quantidade());
        assertEquals(now, json.dataCriacao());
        assertEquals(now, json.dataUltimaAlteracao());
    }

    @Test
    void deveConverterEstoqueParaEstoqueEntity() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Estoque estoque = new Estoque(id, "SKU789", 30L, now, now);

        EstoqueEntity entity = EstoqueUtils.convertToEstoqueEntity(estoque);

        assertEquals(id, entity.getId());
        assertEquals("SKU789", entity.getSku());
        assertEquals(30L, entity.getQuantidade());
        assertEquals(now, entity.getDataCriacao());
        assertEquals(now, entity.getDataUltimaAlteracao());
    }

    @Test
    void deveConverterEstoqueRequestJsonParaEstoque() {
        EstoqueRequestJson request = new EstoqueRequestJson("SKU101", 40L);

        Estoque estoque = EstoqueUtils.convertToEstoque(request);

        assertEquals("SKU101", estoque.getSku());
        assertEquals(40L, estoque.getQuantidade());
        assertNotNull(estoque.getDataCriacao());
        assertNull(estoque.getDataUltimaAlteracao());
    }

    @Test
    void deveConverterIdJsonEEstoqueParaEstoqueEntityAtualizado() {
        UUID id = UUID.randomUUID();
        LocalDateTime dataCriacao = LocalDateTime.now().minusDays(1);
        Estoque existente = new Estoque(id, "SKU202", 50L, dataCriacao, dataCriacao);

        EstoqueRequestJson json = new EstoqueRequestJson("SKU202", 60L);

        EstoqueEntity entity = EstoqueUtils.convertToEstoqueEntity(id, json, existente);

        assertEquals(id, entity.getId());
        assertEquals("SKU202", entity.getSku());
        assertEquals(60L, entity.getQuantidade());
        assertEquals(dataCriacao, entity.getDataCriacao());
        assertNotNull(entity.getDataUltimaAlteracao());
    }

}
