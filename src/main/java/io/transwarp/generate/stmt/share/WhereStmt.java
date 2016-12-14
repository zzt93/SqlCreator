package io.transwarp.generate.stmt.share;

import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.FunctionDepth;
import io.transwarp.generate.stmt.expression.Condition;
import io.transwarp.generate.stmt.expression.Operand;
import io.transwarp.generate.type.DataType;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class WhereStmt implements SqlGeneration{


  private final Condition condition;

  public WhereStmt(Table from) {
    condition = new Condition(from);
  }

  @Override
  public StringBuilder sql() {
    return condition.sql().insert(0, " where ");
  }
}
