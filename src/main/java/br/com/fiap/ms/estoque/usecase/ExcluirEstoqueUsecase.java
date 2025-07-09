package br.com.fiap.ms.estoque.usecase;

import br.com.fiap.ms.estoque.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcluirEstoqueUsecase {

    private final EstoqueGateway estoqueGateway;

    public void excluir(UUID id) {
        try {
            estoqueGateway.excluir(id);
        } catch (DataAccessException e) {
            log.warn("Erro ao excluir o estoque");
            throw new RuntimeException();
        }
    }

}
