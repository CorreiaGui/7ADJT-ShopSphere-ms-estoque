package br.com.fiap.ms.estoque.gateway.database.jpa;

import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.gateway.EstoqueGateway;
import br.com.fiap.ms.estoque.gateway.database.jpa.entity.EstoqueEntity;
import br.com.fiap.ms.estoque.gateway.database.jpa.repository.EstoqueRepository;
import br.com.fiap.ms.estoque.utils.EstoqueUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.fiap.ms.estoque.utils.EstoqueUtils.convertToEstoque;
import static br.com.fiap.ms.estoque.utils.EstoqueUtils.convertToEstoqueEntity;

@Slf4j
@AllArgsConstructor
@Component
public class EstoqueJpaGateway implements EstoqueGateway {

    private final EstoqueRepository estoqueRepository;

    @Override
    public Optional<Estoque> buscarPorSku(String sku) {
        EstoqueEntity estoque = estoqueRepository.findBySku(sku).orElseThrow(() -> new RuntimeException("Sku não encontrado"));
        return Optional.ofNullable(convertToEstoque(estoque));
    }

    @Override
    public List<Estoque> buscarTodoEstoque(int page, int size) {
        Page<EstoqueEntity> estoque = estoqueRepository.findAll(PageRequest.of(page, size));
        return estoque.map(EstoqueUtils::convertToEstoque).getContent();    }

    @Override
    public Optional<Estoque> obterPorSku(String sku) {
        try {
            Optional<EstoqueEntity> estoqueEntityOptional = estoqueRepository.findBySku(sku);

            if(estoqueEntityOptional.isEmpty()) {
                log.info("Estoque não encontrado: sku={}", sku);
                return Optional.empty();
            }

            return Optional.of(convertToEstoque(estoqueEntityOptional.get()));

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID criar(Estoque estoque) {
        try {
            EstoqueEntity estoqueEntity = convertToEstoqueEntity(estoque);

            return estoqueRepository.save(estoqueEntity).getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    @Override
    public Estoque atualizar(EstoqueEntity estoque) {
        EstoqueEntity estoqueEntity = estoqueRepository.save(estoque);
        return convertToEstoque(estoqueEntity);
    }

    @Override
    public void excluir(UUID id) {
        try {
            estoqueRepository.deleteById(id);
        } catch (DataAccessException e) {
            log.error("Erro ao deletar o estoque", e);
            throw new RuntimeException();
        }
    }

    @Override
    public void salvar(Estoque estoque) {
        EstoqueEntity entity = EstoqueUtils.convertToEstoqueEntity(estoque);
        estoqueRepository.save(entity);
    }

}
