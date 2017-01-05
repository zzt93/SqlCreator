package io.transwarp.generate.stmt.expression;

import com.google.common.collect.ObjectArrays;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/20/16.
 * <p>
 * <h3></h3>
 */
public enum DateOp implements Function {
  DATE_FORMAT("DATE_FORMAT(", "to_char(") {
    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.DATE_STRING_GROUP);
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return DATE_WITH_PATTERN;
    }
  },

  TO_DATE("TO_DATE(", "to_date("), TDH_TO_DATE("TDH_TODATE(", "to_date("),;

  public static final GenerationDataType[] DATE_GROUP_ARRAY = {DataTypeGroup.DATE_GROUP};
  public static final GenerationDataType[] DATE_WITH_PATTERN = {DataTypeGroup.DATE_GROUP, DataType.DATE_PATTERN};
  private final String[] ops;

  DateOp(String... s) {
    this.ops = s;
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataTypeGroup.DATE_GROUP);
  }

  @Override
  public Operand apply(Dialect dialect, Operand... input) {
    input[0].sql(dialect).insert(0, ops[dialect.ordinal()])
        .append(Function.PARAMETER_SPLIT)
        .append(input[1].sql(dialect))
        .append(Function.CLOSE_PAREN);
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return new GenerationDataType[]{DataTypeGroup.DATE_STRING_GROUP, DataType.DATE_PATTERN};
  }

  private enum DateArithOp implements Function {

    ADD_MONTHS(new String[]{"ADD_MONTHS(", "ADD_MONTHS("}, new String[]{Function.PARAMETER_SPLIT, Function.PARAMETER_SPLIT}),
    DATE_ADD(new String[]{"DATE_ADD(", "to_char("}, new String[]{Function.PARAMETER_SPLIT, " + "}),
    DATE_SUB(new String[]{"DATE_SUB(", "to_char("}, new String[]{Function.PARAMETER_SPLIT, " - "}),;

    private final String[] ops;
    private final String[] delims;

    DateArithOp(String[] ops, String[] delims) {
      this.ops = ops;
      this.delims = delims;
    }

    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.DATE_STRING_GROUP);
    }

    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      input[0].sql(dialect).insert(0, ops[dialect.ordinal()])
          .append(delims[dialect.ordinal()])
          .append(input[1].sql(dialect))
          .append(Function.CLOSE_PAREN);
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.DATE_GROUP, DataTypeGroup.INT_GROUP};
    }
  }

  /**
   * @see Dialect : notice the mapping between order of dialect and following function name
   */
  private enum CountDateOp implements Function {
    DAY("DAY(", "extract(day from "),
    HOUR("HOUR(", "extract(hour from ") {
      @Override
      public GenerationDataType[] inputTypes(GenerationDataType resultType) {
        return new GenerationDataType[]{DataType.TIMESTAMP};
      }
    },
    MINUTE("MINUTE(", "extract(minute from ") {
      @Override
      public GenerationDataType[] inputTypes(GenerationDataType resultType) {
        return new GenerationDataType[]{DataType.TIMESTAMP};
      }
    },
    SECOND("SECOND(", "extract(second from ") {
      @Override
      public GenerationDataType[] inputTypes(GenerationDataType resultType) {
        return new GenerationDataType[]{DataType.TIMESTAMP};
      }
    },

    DAY_OF_YEAR("DAYOFYEAR(", "to_char(") {
      @Override
      public Operand apply(Dialect dialect, Operand... input) {
        if (dialect == Dialect.ORACLE) {
          input[0].sql(dialect).insert(0, ops[dialect.ordinal()]).append(", 'DDD'").append(Function.CLOSE_PAREN);
        } else {
          super.apply(dialect, input);
        }
        return input[0];
      }
    },
    DAY_OF_WEEK("DAYOFWEEK(", "to_char(") {
      @Override
      public Operand apply(Dialect dialect, Operand... input) {
        if (dialect == Dialect.ORACLE) {
          input[0].sql(dialect).insert(0, ops[dialect.ordinal()]).append(", 'FmDay'").append(Function.CLOSE_PAREN);
        } else {
          super.apply(dialect, input);
        }
        return input[0];
      }
    },
    WEEK_OF_YEAR("WEEKOFYEAR(", "to_char(") {
      @Override
      public Operand apply(Dialect dialect, Operand... input) {
        if (dialect == Dialect.ORACLE) {
          input[0].sql(dialect).insert(0, ops[dialect.ordinal()]).append(", 'WW'").append(Function.CLOSE_PAREN);
        } else {
          super.apply(dialect, input);
        }
        return input[0];
      }
    },
    QUARTER("QUARTER(", "to_char(") {
      @Override
      public Operand apply(Dialect dialect, Operand... input) {
        if (dialect == Dialect.ORACLE) {
          input[0].sql(dialect).insert(0, ops[dialect.ordinal()]).append(", 'Q'").append(Function.CLOSE_PAREN);
        } else {
          super.apply(dialect, input);
        }
        return input[0];
      }
    },

    DATE_DIFF(Function.PARAMETER_SPLIT, " - ") {
      @Override
      public Operand apply(Dialect dialect, Operand... input) {
        input[0].sql(dialect).append(ops[dialect.ordinal()]).append(input[1].sql(dialect));
        if (dialect == Dialect.INCEPTOR) {
          input[0].sql(dialect).insert(0, "DATE_DIFF(").append(Function.CLOSE_PAREN);
        }
        return input[0];
      }

      @Override
      public GenerationDataType[] inputTypes(GenerationDataType resultType) {
        return new GenerationDataType[]{DataTypeGroup.DATE_GROUP, DataTypeGroup.DATE_GROUP};
      }
    },;

    final String[] ops;

    CountDateOp(String... op) {
      this.ops = op;
    }

    @Override
    public void register() {
      FunctionMap.register(this, DataType.INT);
      FunctionMap.register(this, DataTypeGroup.INT_GROUP);
    }

    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      input[0].sql(dialect).insert(0, ops[dialect.ordinal()]).append(Function.CLOSE_PAREN);
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return DATE_GROUP_ARRAY;
    }
  }

  public static Function[] combinedValues() {
    return ObjectArrays.concat(values(),
        ObjectArrays.concat(CountDateOp.values(), DateArithOp.values(), Function.class), Function.class);
  }
}
