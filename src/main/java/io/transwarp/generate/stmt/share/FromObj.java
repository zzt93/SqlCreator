package io.transwarp.generate.stmt.share;

import com.google.common.base.Optional;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.Config;
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
    if (table.name().isPresent()) {
      // update columns
      columns.addAll(table.columns());
      // update sql
      final Dialect base = Config.getBase();
      sql(base)
          .append(" join ")
          .append(table.sql(base))
          .append(" on ")
          .append(condition.sql(base));
    }

    return this;
  }

  public Optional<String> name() {
    return Optional.of(name);
  }

  public ArrayList<Column> columns() {
    return columns;
  }

  public StringBuilder sql(Dialect dialect) {
    return sql;
  }

}
