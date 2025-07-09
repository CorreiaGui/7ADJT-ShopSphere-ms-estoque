package br.com.fiap.ms.estoque.usecase;

import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.gateway.EstoqueGateway;
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
class BuscarEstoquePorSkuUsecaseTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private BuscarEstoquePorSkuUsecase buscarEstoquePorSkuUsecase;

    private Estoque estoque;

    @BeforeEach
    void setUp() {
        estoque = new Estoque(UUID.randomUUID(), "SKU123", 50L,
                LocalDateTime.now().minusDays(1), LocalDateTime.now());
    }

    @Test
    void deveRetornarEstoque_quandoSkuExistir() {
        when(estoqueGateway.buscarPorSku("SKU123")).thenReturn(Optional.of(estoque));

        Estoque resultado = buscarEstoquePorSkuUsecase.buscarEstoquePorSku("SKU123");

        assertNotNull(resultado);
        assertEquals("SKU123", resultado.getSku());
        assertEquals(50L, resultado.getQuantidade());
        verify(estoqueGateway, times(1)).buscarPorSku("SKU123");
    }

    @Test
    void deveLancarExcecao_quandoSkuNaoExistir() {
        when(estoqueGateway.buscarPorSku("SKU404")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                buscarEstoquePorSkuUsecase.buscarEstoquePorSku("SKU404"));

        assertEquals("Sku não encontrado", exception.getMessage());
        verify(estoqueGateway, times(1)).buscarPorSku("SKU404");
    }

}
