package io.transwarp.generate.stmt.expression;

import com.google.common.collect.ObjectArrays;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.generate.util.Strs;

/**
 * Created by zzt on 12/20/16.
 * <p>
 * <h3></h3>
 */
public enum DateOp implements Function {
  DATE_FORMAT("DATE_FORMAT(", "to_char(") {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return DATE_WITH_PATTERN;
    }
  },
  TIMESTAMP_FORMAT("DATE_FORMAT(", "to_char(") {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return TIMESTAMP_WITH_PATTERN;
    }
  },
  DATE_STRING_AND_PATTERN("DATE_FORMAT(", "to_char(") {
    @Override
    public void register() {
      FunctionMap.register(this, DataType.DATE_STRING_WITH_PATTERN);
    }

    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      final Operand apply = super.apply(dialect, input);
      apply.sql(dialect).append(Function.PARAMETER_SPLIT).append(input[1].sql(dialect));
      return apply;
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return DATE_WITH_PATTERN;
    }
  },
  TO_DATE("TO_DATE(", "to_date(") {
    @Override
    public void register() {
      FunctionMap.register(this, DataType.UNIX_DATE);
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataType.DATE_STRING_WITH_PATTERN};
    }
  },;

  public static final GenerationDataType[] DATE_GROUP_ARRAY = {DataTypeGroup.DATE_GROUP};
  public static final GenerationDataType[] DATE_WITH_PATTERN = {DataTypeGroup.DATE_GROUP, DataType.DATE_PATTERN};
  public static final GenerationDataType[] TIMESTAMP_WITH_PATTERN = {DataTypeGroup.DATE_GROUP, DataType.TIMESTAMP_PATTERN};
  private final String[] ops;

  DateOp(String... s) {
    this.ops = s;
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataTypeGroup.STRING_GROUP);
  }

  @Override
  public Operand apply(Dialect dialect, Operand... input) {
    final StringBuilder builder = input[0].sql(dialect).insert(0, ops[dialect.ordinal()]);
    for (int i = 1; i < input.length; i++) {
      builder.append(Function.PARAMETER_SPLIT)
          .append(input[i].sql(dialect));
    }
    builder.append(Function.CLOSE_PAREN);
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return new GenerationDataType[]{DataType.DATE_STRING_WITH_PATTERN};
  }

  private enum DateArithOp implements Function {

    ADD_MONTHS(new String[]{"ADD_MONTHS(", "ADD_MONTHS("}, new String[]{Function.PARAMETER_SPLIT, Function.PARAMETER_SPLIT}),
    DATE_ADD(new String[]{"DATE_ADD(", "to_char("}, new String[]{Function.PARAMETER_SPLIT, " + "}),
    DATE_SUB(new String[]{"DATE_SUB(", "to_char("}, new String[]{Function.PARAMETER_SPLIT, " - "}),
    DATE_DIFF(Strs.of("DATE_DIFF(", "("), Strs.of(Function.PARAMETER_SPLIT, " - ")) {
      @Override
      public void register() {
        FunctionMap.register(this, DataType.INT);
        FunctionMap.register(this, DataTypeGroup.INT_GROUP);
      }

      @Override
      public GenerationDataType[] inputTypes(GenerationDataType resultType) {
        return new GenerationDataType[]{DataTypeGroup.DATE_GROUP, DataTypeGroup.DATE_GROUP};
      }
    },;

    private final String[] ops;
    private final String[] delims;

    DateArithOp(String[] ops, String[] delims) {
      this.ops = ops;
      this.delims = delims;
    }

    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.STRING_GROUP);
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
