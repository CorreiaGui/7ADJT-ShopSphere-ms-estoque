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
class CriarEstoqueUsecaseTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private CriarEstoqueUsecase criarEstoqueUsecase;

    private Estoque novoEstoque;

    @BeforeEach
    void setUp() {
        novoEstoque = new Estoque(UUID.randomUUID(), "SKU999", 50L,
                LocalDateTime.now(), null);
    }

    @Test
    void deveCriarEstoqueComSucessoQuandoSkuNaoExiste() {
        when(estoqueGateway.obterPorSku("SKU999")).thenReturn(Optional.empty());

        UUID idGerado = UUID.randomUUID();
        when(estoqueGateway.criar(novoEstoque)).thenReturn(idGerado);

        UUID resultado = criarEstoqueUsecase.criar(novoEstoque);

        assertEquals(idGerado, resultado);
        verify(estoqueGateway).obterPorSku("SKU999");
        verify(estoqueGateway).criar(novoEstoque);
    }

    @Test
    void deveLancarExcecaoQuandoSkuJaExistente() {
        when(estoqueGateway.obterPorSku("SKU999")).thenReturn(Optional.of(novoEstoque));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> criarEstoqueUsecase.criar(novoEstoque));

        assertNotNull(exception);
        verify(estoqueGateway, times(1)).obterPorSku("SKU999");
        verify(estoqueGateway, never()).criar(any());
    }

}

