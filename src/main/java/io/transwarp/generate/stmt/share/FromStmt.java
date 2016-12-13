package io.transwarp.generate.stmt.share;

import io.transwarp.generate.SqlGeneration;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class FromStmt implements SqlGeneration {

  private FromObj fromObj;
  private StringBuilder sql = new StringBuilder(" where ");

  public FromStmt(FromObj fromObj) {
    this.fromObj = fromObj;
  }

  @Override
  public StringBuilder sql() {
    return sql.append(fromObj.sql());
  }
}
