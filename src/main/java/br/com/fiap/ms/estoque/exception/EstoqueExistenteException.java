package br.com.fiap.ms.estoque.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class EstoqueExistenteException extends SystemBaseException {

    @Serial
    private static final long serialVersionUID = 6198217850168561979L;

    private final String code = "estoque.estoqueJaExiste";

    private final String message = "Estoque já cadastrado.";

    private final Integer httpStatus = 422;

}

