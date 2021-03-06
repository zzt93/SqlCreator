package io.transwarp.generate.stmt.expression;

import com.google.common.collect.ObjectArrays;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.config.TestsConfig;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.generate.util.Strs;

import java.util.EnumMap;

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
      FunctionMap.register(this, DataType.Internal.DATE_STRING_WITH_PATTERN);
    }

    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
      final Operand apply = super.apply(dialects, resultType, input);
      for (Dialect dialect : dialects) {
        apply.sql(dialect).append(Function.PARAMETER_SPLIT).append(input[1].sql(dialect));
      }
      return apply;
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return DATE_WITH_PATTERN;
    }
  },
  TO_DATE("STR_TO_DATE(", "to_date(") {
    @Override
    public void register() {
      FunctionMap.register(this, DataType.UNIX_DATE);
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataType.Internal.DATE_STRING_WITH_PATTERN};
    }
  },;

  public static final GenerationDataType[] DATE_GROUP_ARRAY = {DataTypeGroup.DATE_GROUP};
  public static final GenerationDataType[] DATE_WITH_PATTERN = {DataTypeGroup.DATE_GROUP, DataType.DATE_PATTERN};
  public static final GenerationDataType[] TIMESTAMP_WITH_PATTERN = {DataTypeGroup.DATE_GROUP, DataType.TIMESTAMP_PATTERN};
  final EnumMap<Dialect, String> ops = new EnumMap<>(Dialect.class);

  DateOp(String... ops) {
    for (int i = 0; i < ops.length; i++) {
      Dialect dialect = TestsConfig.getCmpBase()[i];
      this.ops.put(dialect, ops[i]);
    }
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataTypeGroup.STRING_GROUP);
  }

  @Override
  public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
    for (Dialect dialect : dialects) {
      final StringBuilder builder = input[0].sql(dialect).insert(0, ops.get(dialect));
      for (int i = 1; i < input.length; i++) {
        builder.append(Function.PARAMETER_SPLIT)
            .append(input[i].sql(dialect));
      }
      builder.append(Function.CLOSE_PAREN);
    }
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return new GenerationDataType[]{DataType.Internal.DATE_STRING_WITH_PATTERN};
  }

  private enum DateArithOp implements Function {

    ADD_MONTHS(new String[]{"ADD_MONTHS(", "ADD_MONTHS("}, new String[]{Function.PARAMETER_SPLIT, Function.PARAMETER_SPLIT}),
    DATE_ADD(new String[]{"DATE_ADD(", "to_char("}, new String[]{Function.PARAMETER_SPLIT, " + "}),
    DATE_SUB(new String[]{"DATE_SUB(", "to_char("}, new String[]{Function.PARAMETER_SPLIT, " - "}),
    DATE_DIFF(Strs.of("(", "("), Strs.of(" - ", " - ")) {
      @Override
      public void register() {
        FunctionMap.register(this, DataType.INTERVAL);
      }

      @Override
      public GenerationDataType[] inputTypes(GenerationDataType resultType) {
        return new GenerationDataType[]{DataTypeGroup.DATE_GROUP, DataTypeGroup.DATE_GROUP};
      }
    },
    DATE_TIME_ADD(Strs.of("(", "("), Strs.of(" + ", " + ")) {
      @Override
      public void register() {
        FunctionMap.register(this, DataTypeGroup.DATE_GROUP);
      }

      @Override
      public GenerationDataType[] inputTypes(GenerationDataType resultType) {
        if (BiChoicePossibility.HALF.random(true, false)) {
          return new GenerationDataType[]{DataType.INTERVAL, resultType};
        }
        return new GenerationDataType[]{resultType, DataType.INTERVAL};
      }
    },;

    final EnumMap<Dialect, String> delims = new EnumMap<>(Dialect.class);
    final EnumMap<Dialect, String> ops = new EnumMap<>(Dialect.class);

    DateArithOp(String[] ops, String[] delims) {
      for (int i = 0; i < ops.length; i++) {
        Dialect dialect = TestsConfig.getCmpBase()[i];
        this.ops.put(dialect, ops[i]);
        this.delims.put(dialect, delims[i]);
      }
    }

    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.STRING_GROUP);
    }

    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
      for (Dialect dialect : dialects) {
        input[0].sql(dialect).insert(0, ops.get(dialect))
            .append(delims.get(dialect))
            .append(input[1].sql(dialect))
            .append(Function.CLOSE_PAREN);
      }
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.DATE_GROUP, DataTypeGroup.numRandDownCast(DataType.INT)};
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
      public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
        for (Dialect dialect : dialects) {
          if (dialect == Dialect.ORACLE) {
            input[0].sql(dialect).insert(0, ops.get(dialect)).append(", 'DDD'").append(Function.CLOSE_PAREN);
          } else {
            input[0].sql(dialect).insert(0, ops.get(dialect)).append(Function.CLOSE_PAREN);
          }
        }
        return input[0];
      }
    },
    DAY_OF_WEEK("DAYOFWEEK(", "to_char(") {
      @Override
      public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
        for (Dialect dialect : dialects) {
          if (dialect == Dialect.ORACLE) {
            input[0].sql(dialect).insert(0, ops.get(dialect)).append(", 'D'").append(Function.CLOSE_PAREN);
          } else {
            input[0].sql(dialect).insert(0, ops.get(dialect)).append(Function.CLOSE_PAREN);
          }
        }
        return input[0];
      }
    },
    WEEK_OF_YEAR("WEEKOFYEAR(", "to_char(") {
      @Override
      public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
        for (Dialect dialect : dialects) {
          if (dialect == Dialect.ORACLE) {
            input[0].sql(dialect).insert(0, ops.get(dialect)).append(", 'WW'").append(Function.CLOSE_PAREN);
          } else {
            input[0].sql(dialect).insert(0, ops.get(dialect)).append(Function.CLOSE_PAREN);
          }
        }
        return input[0];
      }
    },
    QUARTER("QUARTER(", "to_char(") {
      @Override
      public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
        for (Dialect dialect : dialects) {
          if (dialect == Dialect.ORACLE) {
            input[0].sql(dialect).insert(0, ops.get(dialect)).append(", 'Q'").append(Function.CLOSE_PAREN);
          } else {
            input[0].sql(dialect).insert(0, ops.get(dialect)).append(Function.CLOSE_PAREN);
          }
        }
        return input[0];
      }
    },;

    final EnumMap<Dialect, String> ops = new EnumMap<>(Dialect.class);

    CountDateOp(String... op) {
      for (int i = 0; i < op.length; i++) {
        Dialect dialect = TestsConfig.getCmpBase()[i];
        ops.put(dialect, op[i]);
      }
    }

    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.NUM_GROUP);
    }

    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
      for (Dialect dialect : dialects) {
        input[0].sql(dialect).insert(0, ops.get(dialect)).append(Function.CLOSE_PAREN);
      }
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
