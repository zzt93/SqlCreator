package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.generate.type.ListDataType;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 * <p>
 * <h3>Questions</h3>
 * - <a href="http://stackoverflow.com/questions/25010763/how-to-use-fields-in-java-enum-by-overriding-the-method">
 * how to use fields in java enum by overriding the method</a>
 */
public enum CmpOp implements Function {

  EQ("="),
  NOT_EQ("!="),
  SMALL("<"),
  LARGE(">"),
  SMA_EQ("<="),
  LAR_EQ(">="),
  IS_NULL(" IS NULL") {
    @Override
    public Operand apply(Operand... input) {
      input[0].sql().append(this);
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return ONE_ALL_OPS;
    }
  },
  LIKE(" LIKE "),
  BETWEEN(" BETWEEN ") {
    private static final String and = " AND ";

    @Override
    public Operand apply(Operand... input) {
      input[0].sql()
              .append(this)
              .append(input[1].sql())
              .append(and)
              .append(input[2].sql());
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return Thr_ALL_OPS;
    }

  },
  IN(" IN ") {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return IN_OPS;
    }
  };

  private static final GenerationDataType[] Thr_ALL_OPS = {DataTypeGroup.ALL_GROUP, DataTypeGroup.ALL_GROUP, DataTypeGroup.ALL_GROUP};
  private static final GenerationDataType[] TWO_ALL_OPS = {DataTypeGroup.ALL_GROUP, DataTypeGroup.ALL_GROUP};
  private static final GenerationDataType[] IN_OPS = {DataTypeGroup.ALL_GROUP, ListDataType.ALL_LIST};
  private static final GenerationDataType[] ONE_ALL_OPS = {DataTypeGroup.ALL_GROUP};

  private final String operator;

  CmpOp(String s) {
    this.operator = s;
  }

  @Override
  public String toString() {
    return operator;
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataType.BOOL);
  }

  @Override
  public Operand apply(Operand... input) {
    input[0].sql().append(this).append(input[1].sql());
    input[0].setType(DataType.BOOL);
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return TWO_ALL_OPS;
  }
}
