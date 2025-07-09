package br.com.fiap.ms.estoque.usecase;

import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarTodoEstoqueUsecase {

    private final EstoqueGateway estoqueGateway;

    public List<Estoque> buscarTodoEstoque(int page, int size) {
        return estoqueGateway.buscarTodoEstoque(page, size);
    }

}
