package br.com.fiap.ms.estoque.usecase;

import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.exception.EstoqueInsuficienteException;
import br.com.fiap.ms.estoque.exception.EstoqueNaoEncontradoException;
import br.com.fiap.ms.estoque.exception.QuantidadeInvalidaException;
import br.com.fiap.ms.estoque.gateway.EstoqueGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class AtualizarBaixaEstoqueTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private AtualizarBaixaEstoque atualizarBaixaEstoque;

    @Test
    void deveBaixarQuantidadeDoEstoqueComSucesso() {
        String sku = "SKU123";
        Long quantidadeOriginal = 10L;
        Long quantidadeBaixa = 3L;

        Estoque estoque = new Estoque();
        estoque.setSku(sku);
        estoque.setQuantidade(quantidadeOriginal);

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoque));

        atualizarBaixaEstoque.baixaEstoque(sku, quantidadeBaixa);

        ArgumentCaptor<Estoque> captor = ArgumentCaptor.forClass(Estoque.class);
        verify(estoqueGateway).salvar(captor.capture());
        Estoque estoqueSalvo = captor.getValue();

        assertEquals(quantidadeOriginal - quantidadeBaixa, estoqueSalvo.getQuantidade());
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeForNula() {
        assertThrows(QuantidadeInvalidaException.class, () ->
                atualizarBaixaEstoque.baixaEstoque("SKU", null));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeForZeroOuNegativa() {
        assertThrows(QuantidadeInvalidaException.class, () ->
                atualizarBaixaEstoque.baixaEstoque("SKU", 0L));

        assertThrows(QuantidadeInvalidaException.class, () ->
                atualizarBaixaEstoque.baixaEstoque("SKU", -1L));
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueNaoForEncontrado() {
        when(estoqueGateway.buscarPorSku("SKU")).thenReturn(Optional.empty());

        assertThrows(EstoqueNaoEncontradoException.class, () ->
                atualizarBaixaEstoque.baixaEstoque("SKU", 5L));
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueForInsuficiente() {
        Estoque estoque = new Estoque();
        estoque.setSku("SKU");
        estoque.setQuantidade(2L);

        when(estoqueGateway.buscarPorSku("SKU")).thenReturn(Optional.of(estoque));

        assertThrows(EstoqueInsuficienteException.class, () ->
                atualizarBaixaEstoque.baixaEstoque("SKU", 5L));
    }

}
