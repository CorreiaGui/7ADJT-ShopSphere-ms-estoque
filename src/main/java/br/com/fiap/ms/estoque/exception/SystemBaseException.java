package br.com.fiap.ms.estoque.exception;

import java.io.Serial;

public abstract class SystemBaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3899886033254816856L;

    public abstract String getCode();

    public abstract Integer getHttpStatus();

    @Override
    public abstract String getMessage();

}
