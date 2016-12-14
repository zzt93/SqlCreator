package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.FunctionDepth;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public class Condition implements SqlGeneration{

  private static final GenerationDataType type = DataType.BOOL;
  private final Operand condition;

  public Condition(Table from) {
    final Operand[] operands = Operand.getOperands(from, FunctionDepth.WITH_OPERATOR, DataType.BOOL);
    condition = operands[0];
  }

  @Override
  public StringBuilder sql() {
    return condition.sql();
  }

}
