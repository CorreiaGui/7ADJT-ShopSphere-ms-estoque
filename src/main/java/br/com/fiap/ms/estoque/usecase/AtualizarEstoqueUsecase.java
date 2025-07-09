package br.com.fiap.ms.estoque.usecase;

import br.com.fiap.ms.estoque.controller.json.EstoqueRequestJson;
import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.gateway.EstoqueGateway;
import br.com.fiap.ms.estoque.gateway.database.jpa.entity.EstoqueEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.fiap.ms.estoque.utils.EstoqueUtils.convertToEstoqueEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizarEstoqueUsecase {

    private final EstoqueGateway estoqueGateway;

    public void alterarEstoque(UUID id, EstoqueRequestJson json) {
        Estoque existente = estoqueGateway.buscarPorSku(json.sku())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado - SKU: " + json.sku()));
        EstoqueEntity atualizado = convertToEstoqueEntity(id, json, existente);
        estoqueGateway.atualizar(atualizado);
    }
}
