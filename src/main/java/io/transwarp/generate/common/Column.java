package io.transwarp.generate.common;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.stmt.expression.Operand;
import io.transwarp.generate.type.DataTypeUtil;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 *
 * @see GenerationDataType
 */
public class Column {

  private final Possibility poss;
  private final Operand operand;

  //  private Table table;

  private Column(String name, GenerationDataType type) {
    this(new Operand(type, name, name));
  }

  private Column(Operand operand) {
    this.operand = operand;
    poss = Possibility.NAME_CONST_POSSIBILITY;
  }

  public Column(String name, GenerationDataType type, Table table) {
    this(table.name().isPresent() ? table.name().get() + "." + name : name, type);
  }

  public String[] getNameOrConst(Dialect[] dialects) {
    if (poss.chooseFirst(true, false)) {
      return operand.sqls();
    }
    return DataTypeUtil.randoms(operand.getType(), dialects.length);
  }

  public GenerationDataType getType() {
    return operand.getType();
  }

  public static Column[] fromOperand(Operand... operands) {
    Column[] res = new Column[operands.length];
    for (int i = 0; i < operands.length; i++) {
      res[i] = new Column(operands[i]);
    }
    return res;
  }

  public StringBuilder getName(Dialect dialect) {
    return operand.sql(dialect);
  }
}
