package io.transwarp.generate.common;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.stmt.expression.Operand;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 *
 * @see GenerationDataType
 */
public class Column {

  private static final String EMPTY = "";
  private final Possibility poss;

  private final Operand operand;
  private final String alias;
  private Table table;

  private Column(String name, GenerationDataType type) {
    this(new Operand(type, name, name), EMPTY);
  }

  private Column(Operand operand, String alias) {
    this.operand = operand;
    this.alias = alias;
    poss = Possibility.COL_CONST_POSSIBILITY;
  }

  public Column(String name, GenerationDataType type, Table table) {
    this(name, type);
    this.table = table;
  }

  public Column(Column column, Table table) {
    this(column.operand, column.alias);
    this.table = table;
  }

  public String[] getNameOrConst(Dialect[] dialects) {
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

  public StringBuilder getNameWithAlias(Dialect dialect) {
    assert table != null;
    return new StringBuilder(table.name().get()).append('.')
        .append(operand.sql(dialect)).append(alias);
  }
}
