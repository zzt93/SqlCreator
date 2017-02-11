package io.transwarp.generate.stmt.expression;

import com.google.common.base.Optional;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.config.expr.ExprConfig;
import io.transwarp.generate.config.expr.FunctionDepth;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.generate.type.ListDataType;

import java.util.EnumMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

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

  public Operand(GenerationDataType type, String... ops) {
    this.type = type;
    final Dialect[] dialects = GlobalConfig.getCmpBase();
    assert ops.length == dialects.length;
    for (int i = 0; i < dialects.length; i++) {
      versions.put(dialects[i], new StringBuilder(ops[i]));
    }
  }

  private static Operand makeOperand(GenerationDataType resultType, ExprConfig config, int depth) {
    List<ExprConfig> nextConfig = null;
    if (depth == FunctionDepth.SINGLE) {
      if (config.hasNestedConfig()) {
        nextConfig = config.getOperands();
        // use nested config later to reuse code
      } else {
        final Optional<Column> col = TableUtil.sameTypeRandomCol(config.getFrom(), resultType);
        if (col.isPresent()) {
          final Column column = col.get();
          return new Operand(resultType,
              column.getNameOrConst(GlobalConfig.getCmpBase(), config.getConstOrColumnPossibility()));
        } else {
          if (DataTypeGroup.LIST_GROUP.contains(resultType)) {
            return new Operand(resultType, ((ListDataType) resultType).listOrQuery(config, GlobalConfig.getCmpBase()));
          }
          return new Operand(resultType, resultType.randomData(GlobalConfig.getCmpBase()));
        }
      }
    }
    final Function function = FunctionMap.random(resultType, config.getUdfFilter());
    final GenerationDataType[] inputs = function.inputTypes(resultType);
    Operand[] ops = new Operand[inputs.length];
    final GenerationDataType[] nextResultType = config.getInputRelation().refine(inputs);
    if (nextConfig != null) {
      for (int i = 0; i < nextResultType.length; i++) {
        ExprConfig nextC;
        if (i < nextConfig.size()) {
          nextC = nextConfig.get(i);
        } else {
          nextC = ExprConfig.defaultExpr(config);
        }
        ops[i] = makeOperand(nextResultType[i], nextC, nextC.getUdfDepth());
      }
    } else {
      for (int i = 0; i < nextResultType.length; i++) {
        ops[i] = makeOperand(nextResultType[i], config, depth - 1);
      }
    }
    return function.apply(GlobalConfig.getCmpBase(), resultType, ops)
        .setType(resultType);
  }

  public static Operand[] getOperands(int num, GenerationDataType resultType, ExprConfig config) {
    checkArgument(DataType.outerVisible(resultType));

    final Operand[] res = new Operand[num];
    for (int i = 0; i < num; i++) {
      res[i] = makeOperand(resultType, config, config.getUdfDepth());
    }
    return res;
  }

  public static Operand[] randomOperand(Table[] src, int num, ExprConfig config) {
    final Operand[] res = new Operand[num];
    for (int i = 0; i < num; i++) {
      GenerationDataType type = DataTypeGroup.ALL_GROUP.randomType();
      res[i] = makeOperand(type, config, config.getUdfDepth());
      assert type == res[i].type;
    }
    return res;
  }

  public StringBuilder sql(Dialect dialect) {
    return versions.get(dialect);
  }

  public String[] sqls(Dialect[] dialects) {
    assert dialects.length == versions.size();
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

}
