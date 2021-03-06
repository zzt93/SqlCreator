package io.transwarp.generate.stmt.select;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.expr.TypedExprConfig;
import io.transwarp.generate.config.op.SelectConfig;
import io.transwarp.generate.config.stmt.QueryConfig;
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

  SelectListStmt(SelectConfig config) {
    List<Table> from = config.getTables();
    if (config.selectAll()) {
      cols = TableUtil.columns(from);
      // use star or list all columns
      useStar = config.useStar().random(true, false);
    } else if (config.setPositiveSelectNum()) {
      final int colLimit = config.getSelectNum();
      cols = new ArrayList<>(colLimit);
      // TODO 3/2/17 only random operand, may add random select query
      for (int i = 0; i < colLimit; i++) {
        final TypedExprConfig typedExprConfig = new TypedExprConfig(config.getCandidatesTables(), from);
        cols.addAll(Arrays.asList(Column.fromOperand(
            Operand.getOperands(1, typedExprConfig.getType(), typedExprConfig))));
      }
    } else {
      final List<TypedExprConfig> operands = config.getOperands();
      cols = new ArrayList<>(config.size());
      for (TypedExprConfig operand : operands) {
        cols.addAll(Arrays.asList(Column.fromOperand(
            Operand.getOperands(1, operand.getType(), operand))));
      }
      for (QueryConfig queryConfig : config.getQueries()) {
        if (!queryConfig.selectQuery()) {
          throw new IllegalArgumentException(
              "SubQuery in select statement can only be scalar operand (one column, one row): " + queryConfig.getId());
        }
        final SelectResult result = SelectResult.generateQuery(queryConfig);
        cols.add(Column.fromQuery(result));
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
