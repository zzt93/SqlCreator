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
public enum StringOp implements Function {
  LOWER("LOWER("), LCASE("LCASE("), LTRIM("LTRIM("),
  REPEAT("REPEAT(") {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.STRING_GROUP, DataType.INT};
    }
  },
  REVERSE("REVERSE("),
  LPAD("LPAD(") {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.STRING_GROUP, DataType.INT, DataTypeGroup.STRING_GROUP};
    }
  },
  RPAD("RPAD(") {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.STRING_GROUP, DataType.INT, DataTypeGroup.STRING_GROUP};
    }
  },
  RTRIM("RTRIM("),
  SPACE("SPACE(") {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataType.INT};
    }
  },
  SPLIT("SPLIT(") {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.STRING_GROUP, DataTypeGroup.STRING_GROUP};
    }
  },;

  private final String op;

  StringOp(String s) {
    op = s;
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataTypeGroup.STRING_GROUP);
  }

  @Override
  public Operand apply(Dialect dialect, Operand... input) {
    final StringBuilder builder = input[0].sql(dialect).insert(0, op);
    for (int i = 1; i < inputTypes(DataTypeGroup.STRING_GROUP).length; i++) {
      builder.append(", ").append(input[i].sql(dialect));
    }
    builder.append(Function.CLOSE_PAREN);
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return new GenerationDataType[]{DataTypeGroup.STRING_GROUP};
  }

  private enum VarStringOp implements Function {
    CONCAT, CONCAT_WS, PRINTF;

    @Override
    public void register() {

    }

    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      return null;
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[0];
    }
  }
}
