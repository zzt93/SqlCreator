package io.transwarp.generate.condition;

import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.Config;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/8/16.
 * <p>
 * <h3></h3>
 */
public class Operand {

  private static ThreadLocalRandom random = ThreadLocalRandom.current();
  private String operand;

  private Operand(GenerationDataType type, Table src, int depth) {
    operand = makeOperand(type, src, depth).toString();
  }

  private StringBuilder makeOperand(GenerationDataType resultType, Table src, int depth) {
    if (depth == 0) {
      return new StringBuilder(
              src.randomCol().getNameOrConst());
    } else {
      final StringBuilder sb = makeOperand(resultType, src, depth - 1);
      sb.insert(0, '(').append(')');
      // TODO 12/9/16 add operator or function according to resultType
      return sb;
    }
  }

  /**
   * Assume all operands for this generation are of same type group
   *
   * @param from columns that to choose from
   * @param num  number of operands
   *
   * @return operand array
   */
  static Operand[] randomSameTypeGroupOperand(Table from, int num) {
    final Operand[] res = new Operand[num];
    final ArrayList<Column> columns = from.columns();
    ArrayList<Column> same = new ArrayList<>(columns.size());
    GenerationDataType type = getSameTypeGroupCols(from, same);
    for (int i = 0; i < num; i++) {
      res[i] = new Operand(type, from, Config.getUdfDepth());
    }
    return res;
  }

  public static Operand[] randomOperand(Table src, int num) {
    final Operand[] res = new Operand[num];
    GenerationDataType type = src.randomCol().getType();
    for (int i = 0; i < num; i++) {
      res[i] = new Operand(type, src, Config.getUdfDepth());
    }
    return res;
  }

  private static GenerationDataType getSameTypeGroupCols(Table src, ArrayList<Column> same) {
    final ArrayList<Column> columns = src.columns();
    final GenerationDataType type = src.randomCol().getType();
    final DataTypeGroup group = DataTypeGroup.sameGroup(type);
    for (Column column : columns) {
      if (group.contains(column.getType())) {
        same.add(column);
      }
    }
    return type;
  }

  @Override
  public String toString() {
    return operand;
  }
}
