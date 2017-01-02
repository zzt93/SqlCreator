package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
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
  CAST("cast(") {
    @Override
    public void register() {
    }

    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      // TODO 12/27/16 not implemented: add result type to parameter; mapping back to dialect type name
//      input[0].sql(dialect).insert(0, op).append(" as ").append().append(Function.CLOSE_PAREN);
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
          input = DataTypeGroup.NUM_GROUP;
          break;
        case LIST_GROUP:

        case DATE_GROUP:
          input = (resultType == DataType.UNIX_DATE ? DataType.TIMESTAMP : DataType.UNIX_DATE);
          break;
        case ALL_GROUP:
        case NUM_GROUP:
        default:
          throw new IllegalArgumentException("invalid data type group: decimal + int != num");
      }
      return new GenerationDataType[]{input};
    }
  },
  SAME("") {
    @Override
    public Operand apply(Dialect dialect, Operand... input) {
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
      FunctionMap.register(this, DataTypeGroup.LIST_GROUP);
    }

    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      final StringBuilder builder = input[0].sql(dialect).insert(0, op);
      for (int i = 1; i < input.length; i++) {
        builder.append(Function.PARAMETER_SPLIT).append(input[i].sql(dialect));
      }
      builder.append(Function.CLOSE_PAREN);
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
  };


  final String op;

  ConversionOp(String s) {
    op = s;
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataTypeGroup.ALL_GROUP);
  }

}
