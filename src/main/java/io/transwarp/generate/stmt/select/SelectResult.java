package io.transwarp.generate.stmt.select;

import com.google.common.base.Optional;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.stmt.share.Condition;
import io.transwarp.generate.stmt.share.FromStmt;
import io.transwarp.generate.stmt.share.WhereStmt;

import java.util.ArrayList;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public class SelectResult implements Table {

  private Optional<String> name = Optional.absent();
  private final SelectListStmt selectListStmt;
  private final FromStmt fromStmt;
  private final WhereStmt whereStmt;


  private SelectResult(QueryConfig config) {
    fromStmt = new FromStmt(config.getFrom());
    Table[] from = fromStmt.getTable();

    selectListStmt = new SelectListStmt(from, config.getSelect());
    whereStmt = new WhereStmt(from, config.getWhere());

    addSetOp();
  }

  static SelectResult selectResult(QueryConfig config, Table... src) {
    return new SelectResult(config);
  }

  public static SelectResult simpleQuery(QueryConfig config) {
    return new SelectResult(config);
  }

  private void addSetOp() {
    // TODO 1/10/17 add set op
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
    return subQuery(dialect)
        .append(";");
  }

  private StringBuilder subQuery(Dialect dialect) {
    return new StringBuilder()
        .append(selectListStmt.sql(dialect))
        .append(fromStmt.sql(dialect))
        .append(whereStmt.sql(dialect));
  }

  public String[] subQueries(Dialect[] dialects) {
    String[] res = new String[dialects.length];
    for (int i = 0; i < res.length; i++) {
      res[i] = subQuery(dialects[i]).insert(0, '(').append(')').toString();
    }
    return res;
  }


  // TODO 12/12/16 conversion to operand when only one row one column and requested

}
