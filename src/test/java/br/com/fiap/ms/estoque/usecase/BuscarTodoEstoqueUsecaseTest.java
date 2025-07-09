package br.com.fiap.ms.estoque.usecase;

import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.gateway.EstoqueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class BuscarTodoEstoqueUsecaseTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private BuscarTodoEstoqueUsecase buscarTodoEstoqueUsecase;

    private Estoque estoque1;
    private Estoque estoque2;

    @BeforeEach
    void setUp() {
        estoque1 = new Estoque(UUID.randomUUID(), "SKU001", 10L,
                LocalDateTime.now().minusDays(1), LocalDateTime.now());
        estoque2 = new Estoque(UUID.randomUUID(), "SKU002", 20L,
                LocalDateTime.now().minusDays(2), LocalDateTime.now());
    }

    @Test
    void deveBuscarTodoEstoqueComPaginacao() {
        int page = 0;
        int size = 2;

        when(estoqueGateway.buscarTodoEstoque(page, size))
                .thenReturn(List.of(estoque1, estoque2));

        List<Estoque> resultado = buscarTodoEstoqueUsecase.buscarTodoEstoque(page, size);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(estoque1));
        assertTrue(resultado.contains(estoque2));
        verify(estoqueGateway, times(1)).buscarTodoEstoque(page, size);
    }

}

