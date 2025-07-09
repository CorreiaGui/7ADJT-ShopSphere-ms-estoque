package br.com.fiap.ms.estoque.gateway.database.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estoque", schema = "ms_estoque")
public class EstoqueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "quantidade", nullable = false)
    private Long quantidade;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "data_ultima_alteracao")
    private LocalDateTime dataUltimaAlteracao;
}
