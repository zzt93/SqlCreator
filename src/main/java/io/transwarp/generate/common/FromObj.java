package io.transwarp.generate.common;

import com.google.common.base.Optional;

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
  }


  FromObj join(FromObj table) {
    return this;
  }

  public Optional<String> name() {
    return Optional.of(name);
  }

  public ArrayList<Column> columns() {
    return columns;
  }

  public StringBuilder toSql() {
    return sql;
  }

  @Override
  public Column randomCol() {
    return null;
  }
}
