package io.transwarp.generate.stmt.share;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.stmt.FromConfig;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class FromStmt implements SqlGeneration {

  private static final String FROM = " from ";
  private Table fromObj;

  public FromStmt(FromConfig config, Table[] tables) {
    initFromTable(config, tables);
  }

  @Override
  public StringBuilder sql(Dialect dialect) {
    return new StringBuilder(FROM).append(fromObj.sql(dialect));
  }

  public Table getTable() {
    return fromObj;
  }


  private void initFromTable(FromConfig config, Table[] src) {
    if (config.noJion()) {
      this.fromObj = TableUtil.randomTable(src);
    } else {
//      initFromTable(joinTimes - 1, src);
      // TODO 12/13/16 add join condition; add alias
//      from = from.join(TableUtil.randomTable(src), );
    }
  }
}
