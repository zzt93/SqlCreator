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
  AND(" AND "), OR(" OR "),
  NOT(" NOT ") {
    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... operands) {
      final Operand f = operands[0];
      for (Dialect dialect : dialects) {
        f.sql(dialect).insert(0, LogicalOp.NOT);
      }
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

  private final String operator;

  LogicalOp(String s) {
    operator = s;
  }

  @Override
  public void register() {
    FunctionMap.register(new ParenWrapper(this), DataType.BOOL);
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return TWO_BOOL;
  }

  @Override
  public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... operands) {
    Operand f = operands[0];
    Operand s = operands[1];
    for (Dialect dialect : dialects) {
      f.sql(dialect).append(operator).append(s.sql(dialect));
    }
    return f;
  }
}
