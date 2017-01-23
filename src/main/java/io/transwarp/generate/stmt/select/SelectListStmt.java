package io.transwarp.generate.stmt.select;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.expr.TypedExprConfig;
import io.transwarp.generate.config.op.SelectConfig;
import io.transwarp.generate.stmt.expression.Operand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class SelectListStmt implements SqlGeneration {
  private ArrayList<Column> cols;
  private boolean useStar = false;

  SelectListStmt(Table from, SelectConfig config) {
    if (config.selectAll()) {
      cols = from.columns();
      // use star or list all columns
      useStar = Possibility.HALF.chooseFirstOrRandom(true, false);
    } else if (config.selectRandom()) {
      final int colLimit = config.getSelectNum();
      cols = TableUtil.randomSubCols(from, Possibility.SELECT_COL_POSSIBILITY, colLimit);
      if (cols.size() < colLimit) {
        final int num = colLimit - cols.size();
        cols.addAll(Arrays.asList(Column.fromOperand(Operand.randomOperand(from, num, config.defaultExpr()))));
      }
    } else {
      final List<TypedExprConfig> operands = config.getOperands();
      cols = new ArrayList<>(operands.size());
      for (TypedExprConfig operand : operands) {
        cols.addAll(Arrays.asList(Column.fromOperand(
            Operand.getOperands(from, 1, operand.getType(), operand))));
      }
    }
  }


  ArrayList<Column> getCols() {
    return cols;
  }

  @Override
  public StringBuilder sql(Dialect dialect) {
    final StringBuilder res = new StringBuilder("select ");
    if (useStar) {
      res.append("*");
      return res;
    }
    for (Column col : cols) {
      res.append(col.getNameWithAlias(dialect)).append(", ");
    }
    res.setLength(res.length() - 2);
    return res;
  }
}
