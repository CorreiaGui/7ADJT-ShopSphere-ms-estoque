package br.com.fiap.ms.estoque.usecase;

import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.exception.EstoqueNaoEncontradoException;
import br.com.fiap.ms.estoque.exception.QuantidadeInvalidaException;
import br.com.fiap.ms.estoque.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizarReporEstoqueUsecase {

    private final EstoqueGateway estoqueGateway;

    public void reporEstoque(String sku, Long quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new QuantidadeInvalidaException();
        }

        Estoque estoque = estoqueGateway.buscarPorSku(sku)
                .orElseThrow(EstoqueNaoEncontradoException::new);

        estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        estoqueGateway.salvar(estoque);
    }

}
