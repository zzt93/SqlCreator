package io.transwarp.generate.stmt.select;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.stmt.PerGenerationConfig;
import io.transwarp.generate.stmt.expression.Operand;
import io.transwarp.generate.type.GenerationDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class SelectListStmt implements SqlGeneration {
  private ArrayList<Column> cols;

  SelectListStmt(Table from, PerGenerationConfig config) {
    final int colLimit = config.getSelectColMax();
    if (config.hasResultLimit()) {
      final Map<GenerationDataType, Possibility> results = config.getResults();
      cols = new ArrayList<>(config.getSelectColMax());
      for (GenerationDataType type : results.keySet()) {
        if (results.get(type).chooseFirstOrRandom(true, false)) {
          cols.addAll(Arrays.asList(Column.fromOperand(Operand.getOperands(from, 1, type, config))));
        }
      }
      cols.addAll(TableUtil.randomSubCols(from, colLimit - results.size()));
    } else {
      cols = TableUtil.randomSubCols(from, Possibility.SELECT_COL_POSSIBILITY, colLimit);
      if (cols.size() < colLimit) {
        final int num = Math.min(colLimit - cols.size(), config.getExprNumInSelect());
        cols.addAll(Arrays.asList(Column.fromOperand(Operand.randomOperand(from, num, config))));
      }
    }
  }


  ArrayList<Column> getCols() {
    return cols;
  }

  @Override
  public StringBuilder sql(Dialect dialect) {
    final StringBuilder res = new StringBuilder("select ");
    for (Column col : cols) {
      res.append(col.getNameWithAlias(dialect)).append(", ");
    }
    res.setLength(res.length() - 2);
    return res;
  }
}
