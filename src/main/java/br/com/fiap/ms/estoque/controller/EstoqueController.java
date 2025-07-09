package br.com.fiap.ms.estoque.controller;

import br.com.fiap.ms.estoque.controller.json.EstoqueJson;
import br.com.fiap.ms.estoque.controller.json.EstoqueRequestJson;
import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.usecase.*;
import br.com.fiap.ms.estoque.utils.EstoqueUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static br.com.fiap.ms.estoque.utils.EstoqueUtils.convertToEstoque;
import static br.com.fiap.ms.estoque.utils.EstoqueUtils.convertToEstoqueJson;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EstoqueController.V1_ESTOQUES,
        produces = "application/json")
public class EstoqueController {

    static final String V1_ESTOQUES = "/api/v1/estoques";
    private final BuscarTodoEstoqueUsecase buscarTodoEstoqueUsecase;
    private final BuscarEstoquePorSkuUsecase buscarEstoquePorSkuUsecase;
    private final CriarEstoqueUsecase criarEstoqueUsecase;
    private final AtualizarEstoqueUsecase atualizarEstoqueUsecase;
    private final ExcluirEstoqueUsecase excluirEstoqueUsecase;
    private final AtualizarBaixaEstoque atualizarBaixaEstoque;
    private final AtualizarReporEstoqueUsecase atualizarReporEstoque;

    @GetMapping("/{sku}")
    public ResponseEntity<EstoqueJson> buscarEstoquePorSku(@PathVariable("sku") String sku) {
        log.info("GET | {} | Iniciado busca por Sku | SKU: {}", V1_ESTOQUES, sku);
        Estoque estoque = buscarEstoquePorSkuUsecase.buscarEstoquePorSku(sku);
        if (estoque == null) {
            log.info("GET | {} | Iniciado busca por Sku | SKU: {}", V1_ESTOQUES, sku);
            return ResponseEntity.notFound().build();
        }
        log.info("Estoque encontrado: {}", estoque);
        EstoqueJson estoqueJson = convertToEstoqueJson(estoque);
        log.info("estoqueJson encontrado: {}", estoqueJson);
        return ResponseEntity.ok(estoqueJson);
    }

    @GetMapping
    public ResponseEntity<List<EstoqueJson>> buscarTodoEstoque(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("GET | {} | Iniciado busca de todo estoque com paginacao | page: {} size: {} ", V1_ESTOQUES, page, size);
        List<Estoque> todoEstoque = buscarTodoEstoqueUsecase.buscarTodoEstoque(page, size);
        log.info("GET | {} | Finalizada busca do estoque com paginacao | page: {} size: {} estoque: {}", V1_ESTOQUES, page, size, todoEstoque);
        List<EstoqueJson> estoqueJson = todoEstoque.stream().map(EstoqueUtils::convertToEstoqueJson).toList();
        return ResponseEntity.ok(estoqueJson);
    }

    @PostMapping
    public ResponseEntity<UUID> criar(@Valid @RequestBody EstoqueRequestJson estoqueJson) {
        log.info("POST | {} | Iniciado criação de estoque", V1_ESTOQUES);
        UUID estoque = criarEstoqueUsecase.criar(convertToEstoque(estoqueJson));
        log.info("POST | {} | Finalizada criação de estoque", V1_ESTOQUES);
        return ResponseEntity.ok(estoque);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable("id") UUID id,
                                            @Valid @RequestBody EstoqueRequestJson estoqueRequestJson) {
        log.info("PUT | {} | Iniciado atualização de estoque | Id: {}", V1_ESTOQUES, id);
        atualizarEstoqueUsecase.alterarEstoque(id, estoqueRequestJson);
        log.info("PUT | {} | Finalizada atualização de estoque | Id: {}", V1_ESTOQUES, id);
        return ResponseEntity.ok("Estoque atualizado!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable("id") UUID id) {
        log.info("DELETE | {} | Iniciado exclusão de estoque | Id: {} ", V1_ESTOQUES, id);
        try {
            excluirEstoqueUsecase.excluir(id);
            log.info("DELETE | {} | Finalizada exclusão de estoque | Id: {} ", V1_ESTOQUES, id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("DELETE | {} | Erro ao deletar estoque | Id: {} | Erro: {}", V1_ESTOQUES, id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estoque não encontrado");
        }
    }

    @PostMapping("/baixa")
    public ResponseEntity<String> darBaixaNoEstoque(@RequestParam("sku") String sku,
                                                    @RequestParam("quantidade") Long quantidade) {
        log.info("POST | {} | Iniciando baixa de estoque | SKU: {} | Quantidade: {}", V1_ESTOQUES, sku, quantidade);
        try {
            atualizarBaixaEstoque.baixaEstoque(sku, quantidade);
            log.info("POST | {} | Baixa de estoque concluída | SKU: {}", V1_ESTOQUES, sku);
            return ResponseEntity.ok("Baixa de estoque realizada com sucesso.");
        } catch (RuntimeException e) {
            log.error("POST | {} | Erro na baixa de estoque | SKU: {} | Erro: {}", V1_ESTOQUES, sku, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao realizar baixa de estoque: " + e.getMessage());
        }
    }

    @PostMapping("/repor")
    public ResponseEntity<String> reporEstoque(@RequestParam("sku") String sku,
                                               @RequestParam("quantidade") Long quantidade) {
        log.info("POST | {} | Iniciando reposição de estoque | SKU: {} | Quantidade: {}", V1_ESTOQUES, sku, quantidade);
        try {
            atualizarReporEstoque.reporEstoque(sku, quantidade);
            log.info("POST | {} | Reposição de estoque concluída | SKU: {}", V1_ESTOQUES, sku);
            return ResponseEntity.ok("Reposição de estoque realizada com sucesso.");
        } catch (RuntimeException e) {
            log.error("POST | {} | Erro na reposição de estoque | SKU: {} | Erro: {}", V1_ESTOQUES, sku, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao repor estoque: " + e.getMessage());
        }
    }

}
