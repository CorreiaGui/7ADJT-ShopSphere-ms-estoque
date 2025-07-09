package br.com.fiap.ms.estoque.controller.json;

public record EstoqueRequestJson(String sku,
                                 Long quantidade) {
}
