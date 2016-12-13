package io.transwarp.generate.stmt.share;

import com.google.common.base.Optional;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.stmt.expression.Condition;

import java.util.ArrayList;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public class FromObj implements Table {

  private final String name;
  private ArrayList<Column> columns = new ArrayList<>();
  private StringBuilder sql = new StringBuilder();

  public FromObj(String tableName, ArrayList<Column> columns) {
    this.name = tableName;
    this.columns = columns;
    sql.append(name);
  }


  @Override
  public Table join(Table table, Condition condition) {
    return this;
  }

  public Optional<String> name() {
    return Optional.of(name);
  }

  public ArrayList<Column> columns() {
    return columns;
  }

  public StringBuilder sql() {
    return sql;
  }

}
