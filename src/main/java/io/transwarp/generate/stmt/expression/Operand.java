package io.transwarp.generate.stmt.expression;

import com.google.common.base.Optional;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.Config;
import io.transwarp.generate.config.FunctionDepth;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Created by zzt on 12/8/16.
 * <p>
 * <h3></h3>
 * <br>states</br>
 * <br>operation</br>
 * <br>result</br>
 */
public class Operand {
  /**
   * use @see DataTypeGeneration for this type is for generation
   * and should isolate from the specific sql dialect
   */
  private GenerationDataType type;

  private EnumMap<Dialect, StringBuilder> versions = new EnumMap<>(Dialect.class);

  private Operand(GenerationDataType type, String operand) {
    this(type, operand, operand);
  }

  public Operand(GenerationDataType type, String baseOp, String cmpOp) {
    this.type = type;
    versions.put(Config.getBase(), new StringBuilder(baseOp));
    versions.put(Config.getCmp(), new StringBuilder(cmpOp));
  }

  private static Operand makeOperand(GenerationDataType resultType, Table src, int depth) {
    if (depth == FunctionDepth.SINGLE) {
      final Optional<Column> col = TableUtil.sameTypeRandomCol(src, resultType);
      if (col.isPresent()) {
        final Column column = col.get();
        return new Operand(resultType, column.getNameOrConst(Config.getBase()), column.getNameOrConst(Config.getCmp()));
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
      function.apply(Config.getBase(), ops);
      return function.apply(Config.getCmp(), ops)
          .setType(resultType);
    }
  }

  /**
   * Assume all operands for this generation are of same type group
   *
   * @param from columns that to choose from
   * @param num  number of operands
   * @return operand array
   */
  static Operand[] randomSameTypeGroupOperand(Table from, int num) {
    final ArrayList<Column> columns = from.columns();
    ArrayList<Column> same = new ArrayList<>(columns.size());
    GenerationDataType type = getSameTypeGroupCols(from, same);
    return getOperands(from, num, type);
  }

  public static Operand[] getOperands(Table src, int num, GenerationDataType resultType) {
    final Operand[] res = new Operand[num];
    for (int i = 0; i < num; i++) {
      res[i] = makeOperand(resultType, src, Config.getUdfDepth());
    }
    return res;
  }

  public static Operand[] randomOperand(Table src, int num) {
    final Operand[] res = new Operand[num];
    for (int i = 0; i < num; i++) {
      GenerationDataType type = TableUtil.randomCol(src).getType();
      res[i] = makeOperand(type, src, Config.getUdfDepth());
      //    assert type == operand.type;
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

  public StringBuilder sql(Dialect dialect) {
    return versions.get(dialect);
  }

  public GenerationDataType getType() {
    return type;
  }

  public Operand setType(GenerationDataType type) {
    this.type = type;
    return this;
  }
}
