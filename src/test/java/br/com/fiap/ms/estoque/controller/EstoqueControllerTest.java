package br.com.fiap.ms.estoque.controller;

import br.com.fiap.ms.estoque.controller.json.EstoqueJson;
import br.com.fiap.ms.estoque.controller.json.EstoqueRequestJson;
import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.usecase.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstoqueControllerTest {
    @Mock
    private BuscarTodoEstoqueUsecase buscarTodoEstoqueUsecase;
    @Mock private BuscarEstoquePorSkuUsecase buscarEstoquePorSkuUsecase;
    @Mock private CriarEstoqueUsecase criarEstoqueUsecase;
    @Mock private AtualizarEstoqueUsecase atualizarEstoqueUsecase;
    @Mock private ExcluirEstoqueUsecase excluirEstoqueUsecase;
    @Mock private AtualizarBaixaEstoque atualizarBaixaEstoque;
    @Mock private AtualizarReporEstoqueUsecase atualizarReporEstoque;

    @InjectMocks
    private EstoqueController controller;

    @Test
    void deveRetornarEstoqueQuandoBuscarPorSkuExistente() {
        Estoque estoque = new Estoque();
        when(buscarEstoquePorSkuUsecase.buscarEstoquePorSku("SKU123")).thenReturn(estoque);

        ResponseEntity<EstoqueJson> response = controller.buscarEstoquePorSku("SKU123");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void deveRetornar404QuandoSkuNaoExiste() {
        when(buscarEstoquePorSkuUsecase.buscarEstoquePorSku("SKU999")).thenReturn(null);

        ResponseEntity<EstoqueJson> response = controller.buscarEstoquePorSku("SKU999");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveRetornarListaEstoqueQuandoBuscarTodos() {
        when(buscarTodoEstoqueUsecase.buscarTodoEstoque(0, 10)).thenReturn(List.of(new Estoque()));

        ResponseEntity<List<EstoqueJson>> response = controller.buscarTodoEstoque(0, 10);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void deveCriarEstoqueERetornarUUID() {
        UUID mockId = UUID.randomUUID();
        when(criarEstoqueUsecase.criar(any())).thenReturn(mockId);

        EstoqueRequestJson request = new EstoqueRequestJson("SKU123", 10L);
        ResponseEntity<UUID> response = controller.criar(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockId, response.getBody());
    }

    @Test
    void deveAtualizarEstoque() {
        UUID id = UUID.randomUUID();
        EstoqueRequestJson request = new EstoqueRequestJson("SKU123",15L);

        ResponseEntity<String> response = controller.atualizar(id, request);

        verify(atualizarEstoqueUsecase).alterarEstoque(id, request);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Estoque atualizado!", response.getBody());
    }


    @Test
    void deveExcluirEstoqueComSucesso() {
        UUID id = UUID.randomUUID();

        ResponseEntity<String> response = controller.excluir(id);

        verify(excluirEstoqueUsecase).excluir(id);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveRetornarNotFoundQuandoExcluirFalhar() {
        UUID id = UUID.randomUUID();
        doThrow(new RuntimeException("Erro")).when(excluirEstoqueUsecase).excluir(id);

        ResponseEntity<String> response = controller.excluir(id);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Estoque não encontrado"));
    }

    @Test
    void deveDarBaixaComSucesso() {
        ResponseEntity<String> response = controller.darBaixaNoEstoque("SKU123", 10L);

        verify(atualizarBaixaEstoque).baixaEstoque("SKU123", 10L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deveRetornarErroNaBaixa() {
        doThrow(new RuntimeException("Erro")).when(atualizarBaixaEstoque).baixaEstoque("SKU123", 10L);

        ResponseEntity<String> response = controller.darBaixaNoEstoque("SKU123", 10L);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Erro ao realizar baixa de estoque"));
    }

    @Test
    void deveReporEstoqueComSucesso() {
        ResponseEntity<String> response = controller.reporEstoque("SKU123", 5L);

        verify(atualizarReporEstoque).reporEstoque("SKU123", 5L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Reposição de estoque realizada com sucesso"));
    }

    @Test
    void deveRetornarErroNaReposicao() {
        doThrow(new RuntimeException("Erro")).when(atualizarReporEstoque).reporEstoque("SKU123", 5L);

        ResponseEntity<String> response = controller.reporEstoque("SKU123", 5L);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Erro ao repor estoque"));
    }
}
