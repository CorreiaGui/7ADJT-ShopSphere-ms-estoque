package br.com.fiap.ms.estoque.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class EstoqueInsuficienteException  extends SystemBaseException {

  @Serial
  private static final long serialVersionUID = 6198217850168561979L;

  private final String code = "estoque.estoqueInsuficiente";

  private final String message = "Estoque insuficiente.";

  private final Integer httpStatus = 400;

}