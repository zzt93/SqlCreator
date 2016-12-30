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
import io.transwarp.parse.sql.DDLParser;

import java.util.ArrayList;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public class SelectResult implements Table {

  private Optional<String> name = Optional.absent();
  private Table from;
  private final SelectListStmt selectListStmt;
  private final FromStmt fromStmt;
  private final WhereStmt whereStmt;

  private SelectResult(int colLimit, int joinTimes, int subQueryDepth, Table... src) {
    // TODO 12/29/16 join with sub-query
    makeFromTable(joinTimes, src);
    selectListStmt = new SelectListStmt(from, colLimit);
    fromStmt = new FromStmt(from, subQueryDepth - 1);
    whereStmt = new WhereStmt(from, subQueryDepth - 1);
    // TODO 12/29/16 replace sub-query; add set operation
    addSetOp();
  }

  static SelectResult selectResult(int joinTimes, int subQueryDepth, Table... src) {
    return new SelectResult(Config.getSelectColMax(), joinTimes, subQueryDepth, src);
  }

  public static SelectResult selectResult() {
    return selectResult(Config.getJoinTimes(), Config.getSubQueryDepth(), DDLParser.getTable());
  }

  public static SelectResult simpleQuery(int colLimit, int subQueryDepth) {
    return new SelectResult(colLimit, Config.getJoinTimes(), subQueryDepth, DDLParser.getTable());
  }

  private void addSetOp() {
  }


  private void makeFromTable(int joinTimes, Table[] src) {
    if (joinTimes == 0) {
      this.from = TableUtil.randomTable(src);
    } else {
      makeFromTable(joinTimes - 1, src);
      // TODO 12/13/16 add join condition
//      from = from.join(TableUtil.randomTable(src), );
    }
  }


  @Override
  public Table join(Table table, Condition condition) {
    assert table.name().isPresent();
    if (!name().isPresent()) {
      name = Optional.of(TableUtil.nextAlias());
    }
    // TODO 12/28/16 join: surround '() alias', add columns
    return this;
  }

  public Optional<String> name() {
    return name;
  }

  public ArrayList<Column> columns() {
    return selectListStmt.getCols();
  }

  /**
   * @param dialect sql dialect
   * @return sql stmt
   */
  public StringBuilder sql(Dialect dialect) {
    return new StringBuilder()
        .append(selectListStmt.sql(dialect))
        .append(fromStmt.sql(dialect))
        .append(whereStmt.sql(dialect))
        .append(";");
  }


  // TODO 12/12/16 conversion to operand when only one row one column and requested
  // TODO 12/12/16 conversion to list when only one column and requested

}
