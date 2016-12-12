package io.transwarp.generate.stmt.select;

import com.google.common.base.Optional;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.stmt.expression.Condition;
import io.transwarp.generate.stmt.share.WhereCondition;

import java.util.ArrayList;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public class SelectResult implements Table {

  private Table from;
  private final SelectList selectList;
  private final WhereCondition where;

  SelectResult(Table from) {
    this.from = from;
    selectList = new SelectList(from);
    where = new WhereCondition(from);
  }


  @Override
  public Table join(Table table, Condition condition) {
    return this;
  }

  public Optional<String> name() {
    return Optional.absent();
  }

  public ArrayList<Column> columns() {
    return selectList.getCols();
  }

  public StringBuilder sql() {
    return new StringBuilder()
            .append(selectList.sql())
            .append(from.sql())
            .append(where.sql());
  }

  // TODO 12/12/16 conversion to operand

}
