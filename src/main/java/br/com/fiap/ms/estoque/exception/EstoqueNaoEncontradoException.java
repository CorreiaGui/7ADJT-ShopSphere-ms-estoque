package br.com.fiap.ms.estoque.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class EstoqueNaoEncontradoException extends SystemBaseException {

    @Serial
    private static final long serialVersionUID = 6198217850168561979L;

    private final String code = "estoque.estoqueNaoEncontrado";

    private final String message = "Estoque não encontrado.";

    private final Integer httpStatus = 422;

}
