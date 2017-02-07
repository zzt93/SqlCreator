package io.transwarp.generate.stmt.share;

import com.google.common.base.Optional;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
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


  @Override
  public Table join(Table table, Condition condition) {
    if (table.name().isPresent()) {
      // update columns
      columns.addAll(table.columns());
      // update sql
      for (Dialect dialect : GlobalConfig.getCmpBase()) {
        sql(dialect)
            .append(" join ")
            .append(table.sql(dialect))
            .append(" on ")
            .append(condition.sql(dialect));
      }
    }

    return this;
  }

  public Optional<String> name() {
    return Optional.of(name);
  }

  public ArrayList<Column> columns() {
    return columns;
  }

  @Override
  public Table setAlias(String alias) {
    this.name = alias;
    for (StringBuilder builder : sqls.values()) {
      builder.append(alias);
    }
    return this;
  }

  public StringBuilder sql(Dialect dialect) {
    return sqls.get(dialect);
  }

}
