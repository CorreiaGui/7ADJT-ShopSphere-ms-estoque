package br.com.fiap.ms.estoque.usecase;

import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.exception.EstoqueExistenteException;
import br.com.fiap.ms.estoque.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CriarEstoqueUsecase {

    private final EstoqueGateway estoqueGateway;

    public UUID criar(Estoque estoque) {

        Optional<Estoque> estoqueOptional = estoqueGateway.obterPorSku(estoque.getSku());
        if(estoqueOptional.isPresent()) {
            throw new EstoqueExistenteException();
        }

        return estoqueGateway.criar(estoque);
    }

}
