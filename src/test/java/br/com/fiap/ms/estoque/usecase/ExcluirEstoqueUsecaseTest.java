package br.com.fiap.ms.estoque.usecase;

import br.com.fiap.ms.estoque.gateway.EstoqueGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.dao.DataAccessException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ExcluirEstoqueUsecaseTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private ExcluirEstoqueUsecase excluirEstoqueUsecase;

    @Test
    void deveExcluirEstoqueComSucesso() {
        UUID id = UUID.randomUUID();

        doNothing().when(estoqueGateway).excluir(id);

        assertDoesNotThrow(() -> excluirEstoqueUsecase.excluir(id));

        verify(estoqueGateway, times(1)).excluir(id);
    }

    @Test
    void deveLancarRuntimeException_quandoDataAccessException() {
        UUID id = UUID.randomUUID();

        doThrow(new DataAccessException("Erro ao acessar banco") {}).when(estoqueGateway).excluir(id);

        assertThrows(RuntimeException.class, () -> excluirEstoqueUsecase.excluir(id));

        verify(estoqueGateway, times(1)).excluir(id);
    }

}

