package io.transwarp.generate.stmt.expression;

import com.google.common.base.Optional;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.Config;
import io.transwarp.generate.config.FunctionDepth;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;

import java.util.ArrayList;

/**
 * Created by zzt on 12/8/16.
 * <p>
 * <h3></h3>
 * <br>states</br>
 * <br>operation</br>
 * <br>result</br>
 */
public class Operand implements SqlGeneration {

  private GenerationDataType type;
  private StringBuilder operand;

  private Operand(GenerationDataType type, Table src, int depth) {
    this.type = type;
    final Operand operand = makeOperand(this.type, src, depth);
    this.operand = operand.sql();
//    assert type == operand.type;
  }

  private Operand(GenerationDataType type, String operand) {
    this.type = type;
    this.operand = new StringBuilder(operand);
  }

  private Operand makeOperand(GenerationDataType resultType, Table src, int depth) {
    if (depth == FunctionDepth.SINGLE) {
      final Optional<Column> col = TableUtil.sameTypeRandomCol(src, resultType);
      if (col.isPresent()) {
        return new Operand(resultType, col.get().getNameOrConst());
      } else {
        return new Operand(resultType, resultType.randomData());
      }
    } else {
      final Function function = FunctionMap.random(resultType);
      final GenerationDataType[] inputs = function.inputTypes(resultType);
      Operand[] ops = new Operand[inputs.length];
      final GenerationDataType[] nextResultType = Config.getInputRelation().refine(inputs);
      for (int i = 0; i < nextResultType.length; i++) {
        ops[i] = makeOperand(nextResultType[i], src, depth - 1);
      }
      return function.apply(ops);
    }
  }


  public GenerationDataType getType() {
    return type;
  }

  /**
   * Assume all operands for this generation are of same type group
   *
   * @param from columns that to choose from
   * @param num  number of operands
   *
   * @return operand array
   */
  public static Operand[] randomSameTypeGroupOperand(Table from, int num) {
    final ArrayList<Column> columns = from.columns();
    ArrayList<Column> same = new ArrayList<>(columns.size());
    GenerationDataType type = getSameTypeGroupCols(from, same);
    return getOperands(from, num, type);
  }

  static Operand[] getOperands(Table src, int num, GenerationDataType resultType) {
    final Operand[] res = new Operand[num];
    for (int i = 0; i < num; i++) {
      res[i] = new Operand(resultType, src, Config.getUdfDepth());
    }
    return res;
  }

  public static Operand[] randomOperand(Table src, int num) {
    final Operand[] res = new Operand[num];
    for (int i = 0; i < num; i++) {
      GenerationDataType type = TableUtil.randomCol(src).getType();
      res[i] = new Operand(type, src, Config.getUdfDepth());
    }
    return res;
  }

  private static GenerationDataType getSameTypeGroupCols(Table src, ArrayList<Column> same) {
    final ArrayList<Column> columns = src.columns();
    final GenerationDataType type = TableUtil.randomCol(src).getType();
    final DataTypeGroup group = DataTypeGroup.groupOf(type);
    for (Column column : columns) {
      if (group.contains(column.getType())) {
        same.add(column);
      }
    }
    return type;
  }

  @Override
  public StringBuilder sql() {
    return operand;
  }

  public void setType(GenerationDataType type) {
    this.type = type;
  }
}
