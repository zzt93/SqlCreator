package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.generate.type.ListDataType;

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

  EQ(" = "),
  NOT_EQ(" != "),
  SMALL(" < "),
  LARGE(" > "),
  SMA_EQ(" <= "),
  LAR_EQ(" >= "),
  IS_NULL(" IS NULL") {
    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      input[0].sql(dialect).append(this);
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
    public Operand apply(Dialect dialect, Operand... input) {
      input[0].sql(dialect)
          .append(this)
          .append(input[1].sql(dialect))
          .append(and)
          .append(input[2].sql(dialect));
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
  },
  EXISTS(" EXISTS ") {
    @Override
    public void register() {
    }

    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      input[0].sql(dialect).insert(0, toString());
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{ListDataType.ALL_ONE_COL_QUERY};
    }
  },
  NOT_IN(" NOT IN ") {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return IN_OPS;
    }
  };

  private static final GenerationDataType[] Thr_ALL_OPS = {DataTypeGroup.ALL_GROUP, DataTypeGroup.ALL_GROUP, DataTypeGroup.ALL_GROUP};
  private static final GenerationDataType[] TWO_ALL_OPS = {DataTypeGroup.ALL_GROUP, DataTypeGroup.ALL_GROUP};
  public static final GenerationDataType[] IN_OPS = {DataTypeGroup.ALL_GROUP, ListDataType.ALL_LIST};
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
    FunctionMap.register(new ParenWrapper(this), DataType.BOOL);
  }

  @Override
  public Operand apply(Dialect dialect, Operand... input) {
    input[0].sql(dialect).append(operator).append(input[1].sql(dialect));
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return TWO_ALL_OPS;
  }
}
