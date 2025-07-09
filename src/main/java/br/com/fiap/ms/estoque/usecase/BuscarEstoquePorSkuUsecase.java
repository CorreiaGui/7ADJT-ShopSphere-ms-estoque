package br.com.fiap.ms.estoque.usecase;

import br.com.fiap.ms.estoque.domain.Estoque;
import br.com.fiap.ms.estoque.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarEstoquePorSkuUsecase {

    private final EstoqueGateway estoqueGateway;

    public Estoque buscarEstoquePorSku(String sku) {
        return estoqueGateway.buscarPorSku(sku).orElseThrow(() -> new RuntimeException("Sku não encontrado"));
    }

}
