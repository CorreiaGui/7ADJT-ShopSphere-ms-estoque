package br.com.fiap.ms.estoque.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class QuantidadeInvalidaException extends SystemBaseException {

    @Serial
    private static final long serialVersionUID = 6198217850168561979L;

    private final String code = "estoque.quantidadeErrada";

    private final String message = "Quantidade deve ser maior que zero.";

    private final Integer httpStatus = 400;

}
