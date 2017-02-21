package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.type.DataType;
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
  DIV(" / ") {
    @Override
    public void register() {
      FunctionMap.register(new ParenWrapper(this), DataType.DOUBLE);
    }
  },;


  final String op;

  ArithOp(String s) {
    this.op = s;
  }

  @Override
  public void register() {
    FunctionMap.register(new ParenWrapper(this), DataTypeGroup.NUM_GROUP);
  }

  @Override
  public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
    for (Dialect dialect : dialects) {
      input[0].sql(dialect).append(op).append(input[1].sql(dialect));
    }
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return new GenerationDataType[]{resultType, DataTypeGroup.numRandDownCast(resultType)};
  }
}
