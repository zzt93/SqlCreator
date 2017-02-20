package io.transwarp.generate.stmt.select;

import com.google.common.base.Optional;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.stmt.share.Condition;
import io.transwarp.generate.stmt.share.FromObj;
import io.transwarp.generate.stmt.share.FromStmt;
import io.transwarp.generate.stmt.share.WhereStmt;

import java.util.ArrayList;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public class SelectResult implements Table {

  private static final String EMPTY = "";
  private Optional<String> name = Optional.absent();
  private final SelectListStmt selectListStmt;
  private final FromStmt fromStmt;
  private WhereStmt whereStmt;


  private SelectResult(QueryConfig config) {
    fromStmt = new FromStmt(config.getFrom());

    selectListStmt = new SelectListStmt(config.getSelect());
    // optional statement
    if (config.hasWhere()) {
      whereStmt = new WhereStmt(config.getWhere());
    }

    addSetOp();
  }

  public static SelectResult generateQuery(QueryConfig config) {
    return new SelectResult(config);
  }

  private void addSetOp() {
    // TODO 1/10/17 add set op
  }

  @Override
  public Table join(Table table, Condition condition) {
    return FromObj.join(this, table, condition);
  }

  public Optional<String> name() {
    return name;
  }

  public ArrayList<Column> columns() {
    return selectListStmt.getCols();
  }

  @Override
  public Table setAlias(String alias) {
    this.name = Optional.of(alias);
    return this;
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
        .append(whereStmt != null ? whereStmt.sql(dialect) : EMPTY)
        ;
  }

  public StringBuilder toTable(Dialect dialect) {
    if (!name.isPresent() || TableUtil.invalidAlias(name().get())) {
      setAlias(TableUtil.nextAlias());
    }
    assert name.isPresent();
    return subQuery(dialect).insert(0, '(').append(')').append(name.get());
  }

  public String[] subQueries(Dialect[] dialects) {
    String[] res = new String[dialects.length];
    for (int i = 0; i < res.length; i++) {
      res[i] = subQuery(dialects[i]).insert(0, '(').append(')').toString();
    }
    return res;
  }

}
