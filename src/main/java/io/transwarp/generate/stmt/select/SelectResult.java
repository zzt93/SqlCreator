package io.transwarp.generate.stmt.select;

import com.google.common.base.Optional;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.Config;
import io.transwarp.generate.stmt.expression.Condition;
import io.transwarp.generate.stmt.share.FromStmt;
import io.transwarp.generate.stmt.share.WhereStmt;

import java.util.ArrayList;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public class SelectResult implements Table {

  private Table from;
  private final SelectListStmt selectListStmt;
  private final FromStmt fromStmt;
  private final WhereStmt where;

  SelectResult(int subQueryDepth, Table... src) {
    makeFromTable(subQueryDepth, src);
    selectListStmt = new SelectListStmt(from);
    fromStmt = new FromStmt(from);
    where = new WhereStmt(from);
  }

  private void makeFromTable(int subQueryDepth, Table[] src) {
    if (subQueryDepth == 0) {
      this.from = TableUtil.randomTable(src);
    } else {
      makeFromTable(subQueryDepth - 1, src);
      // TODO 12/13/16 add join condition
//      from = from.join(TableUtil.randomTable(src), );
    }
  }


  @Override
  public Table join(Table table, Condition condition) {
    return this;
  }

  public Optional<String> name() {
    return Optional.absent();
  }

  public ArrayList<Column> columns() {
    return selectListStmt.getCols();
  }

  /**
   * surround with '()' and append a ';' when invoke -- implement by aop,
   * so this method should only invoke once for a SelectResult
   *
   * @return sql stmt
   *
   * @see io.transwarp.generate.common.ParenAspect
   * @param dialect sql dialect
   */
  public StringBuilder sql(Dialect dialect) {
    return new StringBuilder()
        .append(selectListStmt.sql(dialect))
        .append(fromStmt.sql(dialect))
        .append(where.sql(dialect));
  }

  // TODO 12/12/16 conversion to operand when only one row one column and requested
  // TODO 12/12/16 conversion to list when only one column and requested

}
