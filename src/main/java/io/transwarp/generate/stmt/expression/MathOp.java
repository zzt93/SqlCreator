package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/20/16.
 * <p>
 * <h3></h3>
 */
public enum MathOp implements Function {
  ROUND("round(", "round("),
  ROUND_2("round(", "round(") {
    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
      final String[] randomData = DataType.U_BYTE.randomData(dialects);
      for (Dialect dialect : dialects) {
        input[0].sql(dialect).insert(0, ops[dialect.ordinal()]).append(Function.PARAMETER_SPLIT).append(randomData[dialect.ordinal()]).append(CLOSE_PAREN);
      }
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataType.DOUBLE};
    }
  },
  FLOOR("floor(", "floor("), CEIL("ceil(", "ceil("),
  RAND_0("rand(", "dbms_random.value(") {
    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
      String[] data = new String[dialects.length];
      for (int i = 0; i < dialects.length; i++) {
        Dialect dialect = dialects[i];
        data[i] = this.ops[dialect.ordinal()] + CLOSE_PAREN;
      }
      return new Operand(DataType.DOUBLE, data);
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{};
    }
  };

  public static final GenerationDataType[] ONE_DOUBLE = {DataType.DOUBLE};
  final String[] ops;

  MathOp(String... s) {
    this.ops = s;
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataTypeGroup.DECIMAL_GROUP);
  }

  @Override
  public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
    for (Dialect dialect : dialects) {
      input[0].sql(dialect).insert(0, ops[dialect.ordinal()]).append(CLOSE_PAREN);
    }
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return ONE_DOUBLE;
  }
}
