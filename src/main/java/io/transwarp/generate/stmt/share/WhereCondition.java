package io.transwarp.generate.stmt.share;

import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.stmt.expression.CmpCondition;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class WhereCondition implements SqlGeneration{


  private final CmpCondition cmpCondition;

  public WhereCondition(Table from) {
    cmpCondition = new CmpCondition(from);
  }

  @Override
  public StringBuilder sql() {
    return cmpCondition.sql().insert(0, " where ");
  }
}
