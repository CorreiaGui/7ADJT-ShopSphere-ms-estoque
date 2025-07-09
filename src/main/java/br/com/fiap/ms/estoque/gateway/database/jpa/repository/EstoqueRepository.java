package br.com.fiap.ms.estoque.gateway.database.jpa.repository;

import br.com.fiap.ms.estoque.gateway.database.jpa.entity.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EstoqueRepository extends JpaRepository<EstoqueEntity, UUID> {
    Optional<EstoqueEntity> findBySku(String sku);
}
