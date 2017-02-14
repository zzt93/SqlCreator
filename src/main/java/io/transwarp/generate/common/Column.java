package io.transwarp.generate.common;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.stmt.expression.Operand;
import io.transwarp.generate.stmt.select.SelectResult;
import io.transwarp.generate.type.GenerationDataType;

import java.util.ArrayList;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 *
 * @see GenerationDataType
 */
public class Column {

  private static final String EMPTY = "";

  private final Operand operand;
  private final String alias;
  private Table table;

  private Column(String name, GenerationDataType type) {
    this(new Operand(type, name, name), EMPTY);
  }

  private Column(Operand operand, String alias) {
    this.operand = operand;
    this.alias = alias;
  }

  public Column(String name, GenerationDataType type, Table table) {
    this(name, type);
    this.table = table;
  }

  public Column(Column column, Table table) {
    this(column.operand, column.alias);
    this.table = table;
  }

  public String[] getNameOrConst(Dialect[] dialects, Possibility poss) {
    if (poss.chooseFirstOrRandom(true, false)) {
      return operand.sqls(dialects);
    }
    return operand.getType().randomData(dialects);
  }

  public GenerationDataType getType() {
    return operand.getType();
  }

  public static Column[] fromOperand(Operand... operands) {
    Column[] res = new Column[operands.length];
    for (int i = 0; i < operands.length; i++) {
      res[i] = new Column(operands[i], TableUtil.nextColAlias());
    }
    return res;
  }

  public static Column fromQuery(SelectResult selectResult) {
    final ArrayList<Column> columns = selectResult.columns();
    assert columns.size() == 1;
    final GenerationDataType type = columns.get(0).getType();
    return new Column(
        new Operand(type, selectResult.subQueries(GlobalConfig.getCmpBase())),
        TableUtil.nextColAlias());
  }

  public StringBuilder getNameWithAlias(Dialect dialect) {
    StringBuilder sb = new StringBuilder();
    if (table != null) {
      assert table.name().isPresent();
      sb = new StringBuilder(table.name().get()).append('.');
    }
    return sb.append(operand.sql(dialect)).append(alias);
  }
}
