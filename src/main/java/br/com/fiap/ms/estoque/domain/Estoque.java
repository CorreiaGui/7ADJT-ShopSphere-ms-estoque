package br.com.fiap.ms.estoque.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {
    private UUID id;
    private String sku;
    private Long quantidade;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private LocalDateTime dataUltimaAlteracao;

    public Estoque(String sku, Long quantidade) {
        this.sku = sku;
        this.quantidade = quantidade;
        this.dataCriacao = LocalDateTime.now();
        this.dataUltimaAlteracao = null;
    }
}
