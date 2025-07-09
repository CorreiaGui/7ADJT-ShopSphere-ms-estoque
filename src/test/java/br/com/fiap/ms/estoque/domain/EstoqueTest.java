package br.com.fiap.ms.estoque.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EstoqueTest {

    @Test
    void deveCriarEstoqueComConstrutorCompleto() {
        UUID id = UUID.randomUUID();
        String sku = "SKU123";
        Long quantidade = 100L;
        LocalDateTime dataCriacao = LocalDateTime.now().minusDays(1);
        LocalDateTime dataUltimaAlteracao = LocalDateTime.now();

        Estoque estoque = new Estoque(id, sku, quantidade, dataCriacao, dataUltimaAlteracao);

        assertEquals(id, estoque.getId());
        assertEquals(sku, estoque.getSku());
        assertEquals(quantidade, estoque.getQuantidade());
        assertEquals(dataCriacao, estoque.getDataCriacao());
        assertEquals(dataUltimaAlteracao, estoque.getDataUltimaAlteracao());
    }

    @Test
    void deveCriarEstoqueComConstrutorSkuQuantidade() {
        String sku = "SKU456";
        Long quantidade = 50L;

        Estoque estoque = new Estoque(sku, quantidade);

        assertEquals(sku, estoque.getSku());
        assertEquals(quantidade, estoque.getQuantidade());
        assertNotNull(estoque.getDataCriacao());
        assertNull(estoque.getDataUltimaAlteracao());
    }

    @Test
    void deveAlterarValoresComSetters() {
        Estoque estoque = new Estoque();

        UUID id = UUID.randomUUID();
        String sku = "SKU789";
        Long quantidade = 10L;
        LocalDateTime dataCriacao = LocalDateTime.now().minusHours(5);
        LocalDateTime dataUltimaAlteracao = LocalDateTime.now();

        estoque.setId(id);
        estoque.setSku(sku);
        estoque.setQuantidade(quantidade);
        estoque.setDataCriacao(dataCriacao);
        estoque.setDataUltimaAlteracao(dataUltimaAlteracao);

        assertEquals(id, estoque.getId());
        assertEquals(sku, estoque.getSku());
        assertEquals(quantidade, estoque.getQuantidade());
        assertEquals(dataCriacao, estoque.getDataCriacao());
        assertEquals(dataUltimaAlteracao, estoque.getDataUltimaAlteracao());
    }

    @Test
    void deveConstruirEstoqueComBuilder() {
        UUID id = UUID.randomUUID();
        String sku = "SKU999";
        Long quantidade = 75L;
        LocalDateTime dataCriacao = LocalDateTime.now().minusDays(2);
        LocalDateTime dataUltimaAlteracao = LocalDateTime.now();

        Estoque estoque = Estoque.builder()
                .id(id)
                .sku(sku)
                .quantidade(quantidade)
                .dataCriacao(dataCriacao)
                .dataUltimaAlteracao(dataUltimaAlteracao)
                .build();

        assertEquals(id, estoque.getId());
        assertEquals(sku, estoque.getSku());
        assertEquals(quantidade, estoque.getQuantidade());
        assertEquals(dataCriacao, estoque.getDataCriacao());
        assertEquals(dataUltimaAlteracao, estoque.getDataUltimaAlteracao());
    }

}
