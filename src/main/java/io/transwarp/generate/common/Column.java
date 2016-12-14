package io.transwarp.generate.common;

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

  private final Possibility poss;
  /**
   * use @see DataTypeGeneration for this type is for generation
   * and should isolate from the specific sql dialect
   */
  private GenerationDataType type;
  private String name;

  //  private Table table;

  private Column(String name, GenerationDataType type) {
    this.name = name;
    this.type = type;
    this.poss = Possibility.NAME_CONST_POSSIBILITY;
  }

  public Column(String name, GenerationDataType type, Table table) {
    if (table.name().isPresent()) {
      this.name = table.name().get() + "." + name;
    } else {
      this.name = name;
    }
    this.type = type;
    this.poss = Possibility.NAME_CONST_POSSIBILITY;
  }

  @Override
  public String toString() {
    return name;
  }

  public String getNameOrConst() {
    return poss.chooseFirst(toString(), type.getRandom());
  }

  public GenerationDataType getType() {
    return type;
  }

  public static Column[] fromOperand(Operand... operands) {
    Column[] res = new Column[operands.length];
    for (int i = 0; i < operands.length; i++) {
      res[i] = new Column(operands[i].sql().toString(), operands[i].getType());
    }
    return res;
  }
}
