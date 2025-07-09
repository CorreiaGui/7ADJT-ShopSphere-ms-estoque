package br.com.fiap.ms.estoque.usecase;

import br.com.fiap.ms.estoque.domain.Estoque;
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
class AtualizarReporEstoqueUsecaseTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private AtualizarReporEstoqueUsecase usecase;

    @Test
    void deveReporEstoqueComSucesso() {
        String sku = "SKU123";
        Long quantidadeAtual = 5L;
        Long quantidadeRepor = 3L;

        Estoque estoque = new Estoque();
        estoque.setSku(sku);
        estoque.setQuantidade(quantidadeAtual);

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoque));

        usecase.reporEstoque(sku, quantidadeRepor);

        verify(estoqueGateway, times(1)).salvar(Mockito.argThat(e ->
                e.getQuantidade().equals(quantidadeAtual + quantidadeRepor)
        ));
    }

    @Test
    void deveLancarExcecaoSeQuantidadeForNula() {
        assertThrows(QuantidadeInvalidaException.class, () ->
                usecase.reporEstoque("SKU123", null));
    }

    @Test
    void deveLancarExcecaoSeQuantidadeForZeroOuNegativa() {
        assertThrows(QuantidadeInvalidaException.class, () ->
                usecase.reporEstoque("SKU123", 0L));

        assertThrows(QuantidadeInvalidaException.class, () ->
                usecase.reporEstoque("SKU123", -10L));
    }

    @Test
    void deveLancarExcecaoSeEstoqueNaoForEncontrado() {
        when(estoqueGateway.buscarPorSku("NAO_EXISTE")).thenReturn(Optional.empty());

        assertThrows(EstoqueNaoEncontradoException.class, () ->
                usecase.reporEstoque("NAO_EXISTE", 2L));
    }

}

