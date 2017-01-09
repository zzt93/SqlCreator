package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.generate.util.Strs;

/**
 * Created by zzt on 12/30/16.
 * <p>
 * <h3></h3>
 */
public enum BitOp implements Function {
  LOGICAL_AND(Strs.of("(", "bitand("), Strs.of(" & ", ", ")),;

  private final String[] ops;
  private final String[] delim;

  BitOp(String[] ops, String[] delim) {
    this.ops = ops;
    this.delim = delim;
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataTypeGroup.INT_GROUP);
  }

  @Override
  public Operand apply(Dialect dialect, Operand... input) {
    input[0].sql(dialect)
        .insert(0, ops[dialect.ordinal()])
        .append(delim[dialect.ordinal()])
        .append(input[1].sql(dialect))
        .append(Function.CLOSE_PAREN);
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return new GenerationDataType[]{resultType, DataTypeGroup.numRandDownCast(resultType)};
  }
}
