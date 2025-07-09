package br.com.fiap.ms.estoque.gateway;

import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.gateway.database.jpa.entity.EstoqueEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstoqueGateway {
    Optional<Estoque> buscarPorSku(String sku);

    List<Estoque> buscarTodoEstoque(int page, int size);

    Optional<Estoque> obterPorSku(String sku);

    UUID criar(Estoque estoque);

    Estoque atualizar(EstoqueEntity estoque);

    void excluir(UUID id);

    void salvar(Estoque estoque);
}
