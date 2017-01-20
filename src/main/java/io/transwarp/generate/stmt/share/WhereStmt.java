package io.transwarp.generate.stmt.share;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.op.FilterOperatorConfig;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class WhereStmt implements SqlGeneration {


  private static final String WHERE = " where ";
  private final Condition condition;

  public WhereStmt(Table from, FilterOperatorConfig config) {
    condition = new Condition(from, config.getOperand());
  }

  @Override
  public StringBuilder sql(Dialect dialect) {
    return new StringBuilder(WHERE).append(condition.sql(dialect));
  }

}
