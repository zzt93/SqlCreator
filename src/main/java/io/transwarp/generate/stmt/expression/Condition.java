package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.stmt.ContainSubQuery;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public class Condition implements SqlGeneration, ContainSubQuery {

  private static final GenerationDataType type = DataType.BOOL;
  private final Operand condition;

  public Condition(Table from, int subQueryDepth) {
    final Operand[] operands = Operand.getOperands(from, 1, DataType.BOOL, subQueryDepth);
    condition = operands[0];
  }

  @Override
  public StringBuilder sql(Dialect dialect) {
    return condition.sql(dialect);
  }

  @Override
  public void replaceWithSimpleQuery(int subQueryDepth) {
    condition.replaceWithSimpleQuery(subQueryDepth);
  }
}
