package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/16/16.
 * <p>
 * <h3></h3>
 */
public enum ArithOp implements Function{
  PLUS(" + "),
  MINUS(" - "),
  MUL(" * "),
  DIV(" / "),
  MOD(" % "),
  
  ;


  private final String op;

  ArithOp(String s) {
    this.op = s;
  }

  @Override
  public void register() {

  }

  @Override
  public Operand apply(Operand... input) {
    return null;
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return new GenerationDataType[0];
  }

  @Override
  public String toString() {
    return op;
  }
}
