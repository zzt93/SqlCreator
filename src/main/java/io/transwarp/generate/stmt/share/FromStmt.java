package io.transwarp.generate.stmt.share;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.op.JoinConfig;
import io.transwarp.generate.config.stmt.FromConfig;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class FromStmt implements SqlGeneration {

  private static final String FROM = " from ";
  private Table[] fromObj;

  public FromStmt(FromConfig config) {
    initFromTable(config, config.getSrc());
  }

  @Override
  public StringBuilder sql(Dialect dialect) {
    final StringBuilder res = new StringBuilder(FROM);
    for (Table table : fromObj) {
      res.append(table.sql(dialect).append(", "));
    }
    res.setLength(res.length() - 2);
    return res;
  }

  public Table[] getTable() {
    return fromObj;
  }


  private void initFromTable(FromConfig config, Table[] src) {
    if (config.noJoin()) {
      // TODO generate and add subQuery
      fromObj = src;
    } else {
      final JoinConfig join = config.getJoin();
      Table[] tables = TableUtil.getTablesByName(src, join.getOperands());
      Table first = tables[0];
      final Condition condition = new Condition(src, join.getCondition());
      first = first.join(tables[1], condition);
      fromObj = new Table[]{first};
    }
  }
}
