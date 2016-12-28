package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
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
  MOD(" % "),
  LOGICAL_AND(" & ") {
    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.INT_GROUP);
    }
  },
  LOGICAL_OR(" | ") {
    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.INT_GROUP);
    }
  },
  LOGICAL_XOR(" ^ ") {
    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.INT_GROUP);
    }
  },
  LOGICAL_NOT("~ ") {
    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.INT_GROUP);
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{resultType};
    }

    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      input[0].sql(dialect).insert(0, op);
      return input[0];
    }
  },;


  final String op;

  ArithOp(String s) {
    this.op = s;
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataTypeGroup.NUM_GROUP);
  }

  @Override
  public Operand apply(Dialect dialect, Operand... input) {
    input[0].sql(dialect).append(op).append(input[1].sql(dialect));
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return new GenerationDataType[]{resultType, DataTypeGroup.shorterType(resultType)};
  }

  @Override
  public String toString() {
    return op;
  }
}
