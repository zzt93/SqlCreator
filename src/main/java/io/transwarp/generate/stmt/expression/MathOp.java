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
  ROUND("round("),
  ROUND_2("round(") {
    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      input[0].sql(dialect).insert(0, op).append(Function.PARAMETER_SPLIT).append(input[1].sql(dialect)).append(CLOSE_PAREN);
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataType.DOUBLE, DataType.BYTE};
    }
  },
  FLOOR("floor("), CEIL("ceil("),
  RAND("rand(") {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.INT_GROUP};
    }
  },
  RAND_0("rand(") {
    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      final String op = this.op + CLOSE_PAREN;
      return new Operand(DataType.DOUBLE, op, op);
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{};
    }
  }
  ;

  public static final GenerationDataType[] ONE_DOUBLE = {DataType.DOUBLE};
  final String op;

  MathOp(String s) {
    this.op = s;
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataType.DOUBLE);
  }

  @Override
  public Operand apply(Dialect dialect, Operand... input) {
    input[0].sql(dialect).insert(0, op).append(CLOSE_PAREN);
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return ONE_DOUBLE;
  }
}
