package br.com.fiap.ms.estoque.usecase;

import br.com.fiap.ms.estoque.controller.json.EstoqueRequestJson;
import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.gateway.EstoqueGateway;
import br.com.fiap.ms.estoque.gateway.database.jpa.entity.EstoqueEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class AtualizarEstoqueUsecaseTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private AtualizarEstoqueUsecase atualizarEstoqueUsecase;

    private UUID id;
    private EstoqueRequestJson requestJson;
    private Estoque estoqueExistente;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();
        requestJson = new EstoqueRequestJson("SKU123", 50L);
        estoqueExistente = new Estoque(id, "SKU123", 30L,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().minusHours(2));
    }

    @Test
    void deveAlterarEstoqueComSucesso() {
        when(estoqueGateway.buscarPorSku("SKU123")).thenReturn(Optional.of(estoqueExistente));

        atualizarEstoqueUsecase.alterarEstoque(id, requestJson);

        ArgumentCaptor<EstoqueEntity> captor = ArgumentCaptor.forClass(EstoqueEntity.class);
        verify(estoqueGateway).atualizar(captor.capture());

        EstoqueEntity atualizado = captor.getValue();
        assertEquals(id, atualizado.getId());
        assertEquals("SKU123", atualizado.getSku());
        assertEquals(50L, atualizado.getQuantidade());
        assertEquals(estoqueExistente.getDataCriacao(), atualizado.getDataCriacao());
        assertNotNull(atualizado.getDataUltimaAlteracao());
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueNaoForEncontrado() {
        when(estoqueGateway.buscarPorSku("SKU123")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> atualizarEstoqueUsecase.alterarEstoque(id, requestJson));

        assertEquals("Estoque não encontrado - SKU: SKU123", ex.getMessage());
        verify(estoqueGateway, never()).atualizar(any());
    }

}
