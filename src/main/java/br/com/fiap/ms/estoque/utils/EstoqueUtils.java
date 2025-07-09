package br.com.fiap.ms.estoque.utils;

import br.com.fiap.ms.estoque.controller.json.EstoqueJson;
import br.com.fiap.ms.estoque.controller.json.EstoqueRequestJson;
import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.gateway.database.jpa.entity.EstoqueEntity;

import java.util.UUID;

import static java.time.LocalDateTime.now;

public class EstoqueUtils {

    private EstoqueUtils() {}

    public static Estoque convertToEstoque(EstoqueEntity estoque){
        return Estoque.builder()
                .id(estoque.getId())
                .sku(estoque.getSku())
                .quantidade(estoque.getQuantidade())
                .dataCriacao(estoque.getDataCriacao())
                .dataUltimaAlteracao(estoque.getDataUltimaAlteracao())
                .build();
    }

    public static EstoqueJson convertToEstoqueJson(Estoque estoque){
        return new EstoqueJson(
                estoque.getId(),
                estoque.getSku(),
                estoque.getQuantidade(),
                estoque.getDataCriacao(),
                estoque.getDataUltimaAlteracao()
        );
    }

    public static EstoqueEntity convertToEstoqueEntity(Estoque estoque){
        return new EstoqueEntity(
                estoque.getId(),
                estoque.getSku(),
                estoque.getQuantidade(),
                estoque.getDataCriacao(),
                estoque.getDataUltimaAlteracao()
        );
    }

    public static Estoque convertToEstoque(EstoqueRequestJson estoque){
        return new Estoque(
                estoque.sku(),
                estoque.quantidade()
        );
    }

    public static EstoqueEntity convertToEstoqueEntity(UUID id, EstoqueRequestJson json, Estoque existente) {
        return new EstoqueEntity(
                id,
                json.sku(),
                json.quantidade(),
                existente.getDataCriacao(),
                now()
        );
    }

}
