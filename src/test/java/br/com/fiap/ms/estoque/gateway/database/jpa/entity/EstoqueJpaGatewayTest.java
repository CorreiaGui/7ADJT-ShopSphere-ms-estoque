package br.com.fiap.ms.estoque.gateway.database.jpa.entity;

import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.gateway.database.jpa.EstoqueJpaGateway;
import br.com.fiap.ms.estoque.gateway.database.jpa.repository.EstoqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class EstoqueJpaGatewayTest {

    @Mock
    private EstoqueRepository repository;

    @InjectMocks
    private EstoqueJpaGateway gateway;

    private EstoqueEntity entity;
    private Estoque domain;

    @BeforeEach
    void setup() {
        entity = new EstoqueEntity(
                UUID.randomUUID(),
                "SKU123",
                42L,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now()
        );
        domain = new Estoque(
                entity.getId(),
                entity.getSku(),
                entity.getQuantidade(),
                entity.getDataCriacao(),
                entity.getDataUltimaAlteracao()
        );
    }

    @Test
    void buscarPorSku_deveRetornarOptionalEstoque_quandoEncontrar() {
        when(repository.findBySku("SKU123")).thenReturn(Optional.of(entity));

        Optional<Estoque> resultado = gateway.buscarPorSku("SKU123");

        assertTrue(resultado.isPresent());
        assertEquals(entity.getId(), resultado.get().getId());
        assertEquals(entity.getSku(), resultado.get().getSku());
        verify(repository).findBySku("SKU123");
    }

    @Test
    void buscarPorSku_deveLancarRuntimeException_quandoNaoEncontrar() {
        when(repository.findBySku("SKU404")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> gateway.buscarPorSku("SKU404")
        );
        assertEquals("Sku não encontrado", ex.getMessage());
    }

    @Test
    void buscarTodoEstoque_deveRetornarListaDeEstoques() {
        var page = new PageImpl<>(List.of(entity));
        when(repository.findAll(PageRequest.of(0, 5))).thenReturn(page);

        List<Estoque> lista = gateway.buscarTodoEstoque(0, 5);

        assertEquals(1, lista.size());
        assertEquals(entity.getSku(), lista.get(0).getSku());
        verify(repository).findAll(PageRequest.of(0, 5));
    }

    @Test
    void obterPorSku_deveRetornarEmpty_quandoNaoExistir() {
        when(repository.findBySku("SKU999")).thenReturn(Optional.empty());

        Optional<Estoque> opt = gateway.obterPorSku("SKU999");

        assertTrue(opt.isEmpty());
        verify(repository).findBySku("SKU999");
    }

    @Test
    void obterPorSku_deveRetornarEstoque_quandoExistir() {
        when(repository.findBySku("SKU123")).thenReturn(Optional.of(entity));

        Optional<Estoque> opt = gateway.obterPorSku("SKU123");

        assertTrue(opt.isPresent());
        assertEquals(entity.getQuantidade(), opt.get().getQuantidade());
    }

    @Test
    void obterPorSku_deveLancarRuntimeException_quandoRepositoryThrows() {
        when(repository.findBySku("SKU123"))
                .thenThrow(new RuntimeException("BD indisponível"));

        assertThrows(RuntimeException.class,
                () -> gateway.obterPorSku("SKU123")
        );
    }

    @Test
    void criar_deveRetornarId_quandoSalvarComSucesso() {
        EstoqueEntity toSave = new EstoqueEntity(
                null, entity.getSku(), entity.getQuantidade(),
                entity.getDataCriacao(), entity.getDataUltimaAlteracao()
        );
        when(repository.save(any(EstoqueEntity.class))).thenReturn(entity);

        UUID idRetornado = gateway.criar(domain);

        assertEquals(entity.getId(), idRetornado);
        verify(repository).save(argThat(arg ->
                arg.getSku().equals(domain.getSku())
                        && arg.getQuantidade().equals(domain.getQuantidade())
        ));
    }

    @Test
    void criar_deveLancarRuntimeException_quandoRepositoryThrows() {
        when(repository.save(any())).thenThrow(new RuntimeException("Erro"));

        assertThrows(RuntimeException.class,
                () -> gateway.criar(domain)
        );
    }

    @Test
    void atualizar_deveRetornarEstoqueAtualizado() {
        EstoqueEntity updated = new EstoqueEntity(
                entity.getId(),
                entity.getSku(),
                999L,
                entity.getDataCriacao(),
                LocalDateTime.now()
        );
        when(repository.save(entity)).thenReturn(updated);

        Estoque result = gateway.atualizar(entity);

        assertEquals(999L, result.getQuantidade());
        verify(repository).save(entity);
    }

    @Test
    void excluir_deveChamarDeleteById() {
        UUID id = entity.getId();
        doNothing().when(repository).deleteById(id);

        assertDoesNotThrow(() -> gateway.excluir(id));
        verify(repository).deleteById(id);
    }

    @Test
    void excluir_deveLancarRuntimeException_quandoDataAccessException() {
        UUID id = entity.getId();
        doThrow(new DataAccessException("Erro DB"){}).when(repository).deleteById(id);

        assertThrows(RuntimeException.class,
                () -> gateway.excluir(id)
        );
    }

    @Test
    void salvar_deveConverterEDepoisSalvar() {
        Estoque toSave = new Estoque(
                null, "NEW-SKU", 77L,
                LocalDateTime.now(), null
        );

        ArgumentCaptor<EstoqueEntity> captor = ArgumentCaptor.forClass(EstoqueEntity.class);
        when(repository.save(any(EstoqueEntity.class))).thenReturn(entity);

        gateway.salvar(toSave);

        verify(repository).save(captor.capture());
        EstoqueEntity saved = captor.getValue();
        assertEquals("NEW-SKU", saved.getSku());
        assertEquals(77L, saved.getQuantidade());
    }

}

