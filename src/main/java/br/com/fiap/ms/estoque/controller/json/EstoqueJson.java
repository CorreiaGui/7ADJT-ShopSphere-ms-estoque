package br.com.fiap.ms.estoque.controller.json;

import java.time.LocalDateTime;
import java.util.UUID;

public record EstoqueJson(UUID id,
                          String sku,
                          Long quantidade,
                          LocalDateTime dataCriacao,
                          LocalDateTime dataUltimaAlteracao) {
}
