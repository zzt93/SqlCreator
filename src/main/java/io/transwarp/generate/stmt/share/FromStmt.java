package io.transwarp.generate.stmt.share;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class FromStmt implements SqlGeneration {

  private static final String FROM = " from ";
  private Table fromObj;

  public FromStmt(Table fromObj) {
    this.fromObj = fromObj;
  }

  @Override
  public StringBuilder sql(Dialect dialect) {
    return new StringBuilder(FROM).append(fromObj.sql(dialect));
  }
}
