package io.transwarp.generate.stmt.select;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.Config;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.stmt.expression.Operand;

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
    this(from, Possibility.SELECT_COL_POSSIBILITY);
  }

  SelectListStmt(Table from, int colLimit) {
    cols = TableUtil.randomSubCols(from, Possibility.SELECT_COL_POSSIBILITY, colLimit);
    if (cols.size() < colLimit) {
      final int num = Math.min(colLimit - cols.size(), Config.getExprNumInSelect());
      cols.addAll(Arrays.asList(Column.fromOperand(Operand.randomOperand(from, num))));
    }
  }

  private SelectListStmt(Table from, Possibility possibility) {
    cols = TableUtil.randomSubCols(from, possibility);
    cols.addAll(Arrays.asList(Column.fromOperand(Operand.randomOperand(from, Config.getExprNumInSelect()))));
  }

  ArrayList<Column> getCols() {
    return cols;
  }

  @Override
  public StringBuilder sql(Dialect dialect) {
    final StringBuilder res = new StringBuilder("select ");
    for (Column col : cols) {
      res.append(col.getName(dialect)).append(", ");
    }
    res.setLength(res.length() - 2);
    return res;
  }
}
