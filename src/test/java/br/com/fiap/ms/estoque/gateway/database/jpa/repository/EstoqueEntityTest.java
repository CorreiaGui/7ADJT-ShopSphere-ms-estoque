package br.com.fiap.ms.estoque.gateway.database.jpa.repository;

import br.com.fiap.ms.estoque.gateway.database.jpa.entity.EstoqueEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueEntityTest {

    @Test
    void deveCobrirTodosOsGettersESetters() {
        UUID id = UUID.randomUUID();
        String sku = "SKU-123";
        Long quantidade = 10L;
        LocalDateTime agora = LocalDateTime.now();

        EstoqueEntity entity = new EstoqueEntity();
        entity.setId(id);
        entity.setSku(sku);
        entity.setQuantidade(quantidade);
        entity.setDataCriacao(agora);
        entity.setDataUltimaAlteracao(agora);

        assertEquals(id, entity.getId());
        assertEquals(sku, entity.getSku());
        assertEquals(quantidade, entity.getQuantidade());
        assertEquals(agora, entity.getDataCriacao());
        assertEquals(agora, entity.getDataUltimaAlteracao());
    }
}
