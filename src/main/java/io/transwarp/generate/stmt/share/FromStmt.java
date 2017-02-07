package io.transwarp.generate.stmt.share;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.stmt.FromConfig;

import java.util.List;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class FromStmt implements SqlGeneration {

  private static final String FROM = " from ";
  private List<Table> fromObj;

  public FromStmt(FromConfig config) {
    fromObj = config.getFromObj();
  }

  @Override
  public StringBuilder sql(Dialect dialect) {
    final StringBuilder res = new StringBuilder(FROM);
    for (Table table : fromObj) {
      res.append(table.sql(dialect)).append(", ");
    }
    res.setLength(res.length() - 2);
    return res;
  }

}
