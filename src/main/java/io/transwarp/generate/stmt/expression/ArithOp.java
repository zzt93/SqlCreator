package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/16/16.
 * <p>
 * <h3></h3>
 */
public enum ArithOp implements Function {
  PLUS(" + "),
  MINUS(" - "),
  MUL(" * "),
  DIV(" / "),
  MOD(" % "),;


  private final String op;

  ArithOp(String s) {
    this.op = s;
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataTypeGroup.ALL_GROUP);
  }

  @Override
  public Operand apply(Operand... input) {
    input[0].sql().append(op).append(input[1].sql());
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return new GenerationDataType[]{resultType, resultType};
  }

  @Override
  public String toString() {
    return op;
  }
}
