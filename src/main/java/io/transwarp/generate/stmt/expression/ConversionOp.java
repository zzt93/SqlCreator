package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.generate.type.ListDataType;

import java.util.Arrays;

/**
 * Created by zzt on 12/20/16.
 * <p>
 * <h3></h3>
 */
public enum ConversionOp implements Function {
  BASE64("BASE64(") {
    @Override
    public void register() {
    }

    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
      return null;
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[0];
    }
  },
  CAST("cast(") {
    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.ALL_BUT_BOOL_BINARY_LIST);
    }

    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
      for (Dialect dialect : dialects) {
        input[0].sql(dialect).insert(0, op)
            .append(" as ")
            .append(dialect.getOriginType(resultType).getName())
            .append(Function.CLOSE_PAREN);
      }
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      final DataTypeGroup group = DataTypeGroup.groupOf(resultType);
      GenerationDataType input;
      switch (group) {
        case DECIMAL_GROUP:
          input = DataTypeGroup.INT_GROUP;
          break;
        case INT_GROUP:
          input = DataTypeGroup.DECIMAL_GROUP;
          break;
        case STRING_GROUP:
          input = Possibility.HALF.chooseFirstOrRandom(DataTypeGroup.NUM_GROUP, DataTypeGroup.DATE_GROUP);
          break;
        case DATE_GROUP:
          input = (resultType == DataType.UNIX_DATE ? DataType.TIMESTAMP : DataType.UNIX_DATE);
          break;
        case LIST_GROUP:
        case ALL_GROUP:
        case NUM_GROUP:
        default:
          throw new IllegalArgumentException("invalid data type group: " + group);
      }
      return new GenerationDataType[]{input};
    }
  },
  SAME("") {
    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.ALL_GROUP);
    }

    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{resultType};
    }
  },
  LIST("(") {
    @Override
    public void register() {
      FunctionMap.register(this, ListDataType.ALL_LIST);
    }

    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
      for (Dialect dialect : dialects) {
        final StringBuilder builder = input[0].sql(dialect).insert(0, op);
        for (int i = 1; i < input.length; i++) {
          builder.append(Function.PARAMETER_SPLIT).append(input[i].sql(dialect));
        }
        builder.append(Function.CLOSE_PAREN);
      }
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      assert resultType instanceof ListDataType;
      final GenerationDataType type = ((ListDataType) resultType).getType();
      final GenerationDataType[] res = new GenerationDataType[((ListDataType) resultType).getLen()];
      Arrays.fill(res, type);
      return res;
    }
  },
  SUB_QUERY("") {
    @Override
    public void register() {
      FunctionMap.register(this, ListDataType.ALL_ONE_COL_QUERY);
    }

    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{ListDataType.ALL_ONE_COL_QUERY};
    }
  },;


  final String op;

  ConversionOp(String s) {
    op = s;
  }


}
