package io.transwarp.generate.stmt.share;

import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class FromStmt implements SqlGeneration {

  private Table fromObj;
  private StringBuilder sql = new StringBuilder(" from ");

  public FromStmt(Table fromObj) {
    this.fromObj = fromObj;
  }

  @Override
  public StringBuilder sql() {
    return sql.append(fromObj.sql());
  }
}
