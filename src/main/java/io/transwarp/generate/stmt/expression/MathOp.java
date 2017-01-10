package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.type.DataType;
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
    public Operand apply(Dialect dialect, Operand... input) {
      input[0].sql(dialect).insert(0, ops[dialect.ordinal()]).append(Function.PARAMETER_SPLIT).append(input[1].sql(dialect)).append(CLOSE_PAREN);
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataType.DOUBLE, DataType.BYTE};
    }
  },
  FLOOR("floor(", "floor("), CEIL("ceil(", "ceil("),
  RAND_0("rand(", "dbms_random.value(") {
    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      final String op = this.ops[dialect.ordinal()] + CLOSE_PAREN;
      return new Operand(DataType.DOUBLE, op, op);
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{};
    }
  }
  ;

  public static final GenerationDataType[] ONE_DOUBLE = {DataType.DOUBLE};
  final String[] ops;

  MathOp(String... s) {
    this.ops = s;
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataType.DOUBLE);
  }

  @Override
  public Operand apply(Dialect dialect, Operand... input) {
    input[0].sql(dialect).insert(0, ops[dialect.ordinal()]).append(CLOSE_PAREN);
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return ONE_DOUBLE;
  }
}
