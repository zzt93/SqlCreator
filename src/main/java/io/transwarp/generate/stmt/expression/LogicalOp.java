package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/5/16.
 * <p>
 * <h3></h3>
 */
public enum LogicalOp implements Function {
  AND(" AND ") , OR(" OR "),
  NOT(" NOT ") {
    @Override
    public Operand apply(Dialect dialect, Operand... operands) {
      final Operand f = operands[0];
      f.sql(dialect).insert(0, LogicalOp.NOT);
      return f;
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataType.BOOL};
    }
  };

  private static final GenerationDataType[] TWO_BOOL = {
          DataType.BOOL, DataType.BOOL
  };

  private final StringBuilder name;

  LogicalOp(String s) {
    name = new StringBuilder(s);
  }

  @Override
  public String toString() {
    return name.toString();
  }

  @Override
  public void register() {
    FunctionMap.register(new FunctionMap.FunctionWrapper(this), DataType.BOOL);
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return TWO_BOOL;
  }

  @Override
  public Operand apply(Dialect dialect, Operand... operands) {
    Operand f = operands[0];
    Operand s = operands[1];
    f.sql(dialect).append(this).append(s.sql(dialect));
    return f;
  }
}
