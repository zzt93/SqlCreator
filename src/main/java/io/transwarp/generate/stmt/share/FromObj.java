package io.transwarp.generate.stmt.share;

import com.google.common.base.Optional;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.GlobalConfig;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public class FromObj implements Table {

  private String name;
  private ArrayList<Column> columns = new ArrayList<>();
  private EnumMap<Dialect, StringBuilder> sqls = new EnumMap<>(Dialect.class);

  public FromObj(String tableName, ArrayList<Column> columns) {
    this.name = tableName;
    this.columns = columns;
    initSql();
  }

  private void initSql() {
    for (Dialect dialect : GlobalConfig.getCmpBase()) {
      sqls.put(dialect, new StringBuilder(name));
    }
  }

  public FromObj(Table table) {
    name = table.name().get();
    for (Column column : table.columns()) {
      columns.add(new Column(column, this));
    }
    initSql();
  }


  private boolean setAlias = false;
  @Override
  public Table join(Table table, Condition condition) {
    if (!setAlias) {
      setAlias(TableUtil.nextAlias());
    }
    return join(this, table, condition);
  }

  public static Table join(Table t1, Table t2, Condition condition) {
    // update columns
    t1.columns().addAll(t2.columns());
    // update sql
    for (Dialect dialect : GlobalConfig.getCmpBase()) {
      t1.toTableSql(dialect)
          .append(" join ")
          .append(t2.toTableSql(dialect))
          .append(" on ")
          .append(condition.sql(dialect));
    }
    return t1;
  }

  @Override
  public StringBuilder toTableSql(Dialect dialect) {
    return sql(dialect);
  }

  public Optional<String> name() {
    return Optional.of(name);
  }

  public ArrayList<Column> columns() {
    return columns;
  }

  @Override
  public Table setAlias(String alias) {
    if (TableUtil.invalidAlias(alias)) {
      alias = TableUtil.nextAlias();
    }
    this.name = alias;
    assert !setAlias;
    setAlias = true;
    for (StringBuilder builder : sqls.values()) {
      builder.append(" ").append(alias);
    }
    return this;
  }

  @Override
  public Table toTable(String alias) {
    return TableUtil.deepCopy(this).setAlias(alias);
  }

  public StringBuilder sql(Dialect dialect) {
    return sqls.get(dialect);
  }

}
