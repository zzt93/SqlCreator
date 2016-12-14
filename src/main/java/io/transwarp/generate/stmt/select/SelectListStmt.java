package io.transwarp.generate.stmt.select;

import com.google.common.base.Joiner;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.stmt.expression.Operand;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class SelectListStmt implements SqlGeneration {
  private ArrayList<Column> cols;

  SelectListStmt(Table from) {
    this(from, Possibility.SELECT_POSSIBILITY);
  }

  private SelectListStmt(Table from, Possibility possibility) {
    cols = TableUtil.randomSubTable(from, possibility);
    cols.addAll(Arrays.asList(Column.fromOperand(Operand.randomOperand(from, 0))));
  }

  ArrayList<Column> getCols() {
    return cols;
  }

  @Override
  public StringBuilder sql() {
    final Joiner joiner = Joiner.on(", ");
    return new StringBuilder("select ").append(joiner.join(cols));
  }
}
