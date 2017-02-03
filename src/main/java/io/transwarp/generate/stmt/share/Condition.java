package io.transwarp.generate.stmt.share;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.expr.ExprConfig;
import io.transwarp.generate.stmt.expression.Operand;
import io.transwarp.generate.type.DataType;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public class Condition implements SqlGeneration {

  private final Operand condition;

  public Condition(Table[] src, ExprConfig config) {
    final Operand[] operands = Operand.getOperands(src, 1, DataType.BOOL, config);
    condition = operands[0];
  }

  @Override
  public StringBuilder sql(Dialect dialect) {
    return condition.sql(dialect);
  }

}
