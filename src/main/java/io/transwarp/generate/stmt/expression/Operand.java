package io.transwarp.generate.stmt.expression;

import com.google.common.base.Optional;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.FunctionDepth;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.stmt.ContainSubQuery;
import io.transwarp.generate.stmt.select.QueryGenerator;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.generate.type.ListDataType;

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
public class Operand implements ContainSubQuery {
  /**
   * use @see DataTypeGeneration for this type is for generation
   * and should isolate from the specific sql dialect
   */
  private GenerationDataType type;

  private EnumMap<Dialect, StringBuilder> versions = new EnumMap<>(Dialect.class);

  private Operand(GenerationDataType type, String operand) {
    this(type, operand, operand);
  }

  public Operand(GenerationDataType type, String... ops) {
    this.type = type;
    versions.put(GlobalConfig.getBase(), new StringBuilder(ops[0]));
    versions.put(GlobalConfig.getCmp(), new StringBuilder(ops[1]));
  }

  private static Operand makeOperand(GenerationDataType resultType, Table src, int depth, boolean moreSubQuery) {
    if (depth == FunctionDepth.SINGLE) {
      final Optional<Column> col = TableUtil.sameTypeRandomCol(src, resultType);
      if (col.isPresent()) {
        final Column column = col.get();
        return new Operand(resultType, column.getNameOrConst(GlobalConfig.getBaseCmp()));
      } else {
        return new Operand(resultType, resultType.randomData());
      }
    } else {
      final Function function = FunctionMap.random(resultType, GlobalConfig.getUdfChooseOption().subQuery(moreSubQuery));
      final GenerationDataType[] inputs = function.inputTypes(resultType);
      Operand[] ops = new Operand[inputs.length];
      final GenerationDataType[] nextResultType = GlobalConfig.getInputRelation().refine(inputs);
      for (int i = 0; i < nextResultType.length; i++) {
        ops[i] = makeOperand(nextResultType[i], src, depth - 1, moreSubQuery);
      }
      function.apply(GlobalConfig.getBase(), ops);
      return function.apply(GlobalConfig.getCmp(), ops)
          .setType(resultType);
    }
  }

  public static Operand[] getOperands(Table src, int num, GenerationDataType resultType, int queryDepth) {
    final Operand[] res = new Operand[num];
    for (int i = 0; i < num; i++) {
      res[i] = makeOperand(resultType, src, GlobalConfig.getUdfDepth(), queryDepth > 0);
    }
    return res;
  }

  public static Operand[] randomOperand(Table src, int num) {
    final Operand[] res = new Operand[num];
    for (int i = 0; i < num; i++) {
      GenerationDataType type = TableUtil.randomCol(src).getType();
      res[i] = makeOperand(type, src, GlobalConfig.getUdfDepth(), false);
      assert type == res[i].type;
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

  public String[] sqls() {
    String[] res = new String[versions.size()];
    int i = 0;
    for (Dialect dialect : versions.keySet()) {
      res[i++] = sql(dialect).toString();
    }
    return res;
  }

  public GenerationDataType getType() {
    return type;
  }

  public Operand setType(GenerationDataType type) {
    this.type = type;
    return this;
  }

  @Override
  public void replaceWithSimpleQuery(int queryDepth) {
    final QueryGenerator generator = new QueryGenerator(queryDepth, 1);
    for (Dialect dialect : GlobalConfig.getBaseCmp()) {
      generator.replaceAll(versions.get(dialect), dialect, ListDataType.SUB_QUERY_TO_REPLACE);
    }
  }

}
