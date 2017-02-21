package io.transwarp.generate.stmt.expression;

import com.google.common.collect.ObjectArrays;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.FixedParams;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.generate.util.Strs;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/20/16.
 * <p>
 * <h3></h3>
 */
public enum StringOp implements Function {
  LOWER("LOWER("), LTRIM("LTRIM("),
  REPEAT("REPEAT(") {
    @Override
    public void register() {
      // TODO 1/10/17 implement using lpad(str, n*length(str), str)
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.STRING_GROUP, DataTypeGroup.UINT_GROUP};
    }
  },
  REVERSE("REVERSE("),
  LPAD("LPAD(") {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.STRING_GROUP, DataType.U_BYTE, DataTypeGroup.STRING_GROUP};
    }
  },
  RPAD("RPAD(") {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.STRING_GROUP, DataType.U_BYTE, DataTypeGroup.STRING_GROUP};
    }
  },
  RTRIM("RTRIM("),;

  private final String op;

  StringOp(String s) {
    op = s;
  }

  @Override
  public void register() {
    FunctionMap.register(this, DataTypeGroup.STRING_GROUP);
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
    return new GenerationDataType[]{DataTypeGroup.STRING_GROUP};
  }

  private enum VarStringOp implements Function {
    CONCAT(Strs.of("CONCAT(", "("), Strs.of(", ", " || ")),
    CONCAT_WS(Strs.of("CONCAT_WS(", "("), Strs.of(", ", " || ")) {
      @Override
      GenerationDataType[] base() {
        return new GenerationDataType[]{DataTypeGroup.STRING_GROUP, DataTypeGroup.STRING_GROUP};
      }
    },
    PRINTF("PRINTF(", "(") {
      @Override
      public void register() {
        // TODO Argument 1 of function PRINTF must be "string", but "varchar(50)" was found.
      }

      @Override
      public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
        // TODO 12/23/16 not fully implemented
        for (Dialect dialect : dialects) {
          input[0].sql(dialect).insert(0, ops[dialect.ordinal()]).append(Function.CLOSE_PAREN);
        }
        return input[0];
      }
    };

    final String[] ops;
    final ThreadLocalRandom random = ThreadLocalRandom.current();
    private String[] delims;

    VarStringOp(String... s) {
      ops = s;
    }


    VarStringOp(String[] ops, String[] delims) {
      this.ops = ops;
      this.delims = delims;
    }

    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.STRING_GROUP);
    }

    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... inputs) {
      for (Dialect dialect : dialects) {
        final StringBuilder builder = inputs[0].sql(dialect).insert(0, ops[dialect.ordinal()]);
        for (int i = 1; i < inputs.length; i++) {
          builder.append(delims[dialect.ordinal()]).append(inputs[i].sql(dialect));
        }
        builder.append(Function.CLOSE_PAREN);
      }
      return inputs[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      final GenerationDataType[] base = base();
      final int len = base.length;
      assert len >= 1;
      // the last argument type is variable len type: printf(string, obj...), base=[string, obj]
      final GenerationDataType varType = base[len - 1];
      final GenerationDataType[] res = new GenerationDataType[random.nextInt(FixedParams.VAR_ARGS_MAX_LEN) + len];
      Arrays.fill(res, varType);
      System.arraycopy(base, 0, res, 0, len);
      return res;
    }

    GenerationDataType[] base() {
      return new GenerationDataType[]{DataTypeGroup.STRING_GROUP};
    }
  }

  private enum StringToIntOp implements Function {
    ASCII("ASCII(", "ASCII("),
    INSTR("INSTR(", "instr(") {
      @Override
      public GenerationDataType[] inputTypes(GenerationDataType resultType) {
        return new GenerationDataType[]{DataTypeGroup.STRING_GROUP, DataTypeGroup.STRING_GROUP};
      }
    },
    LENGTH("LENGTH(", "length("),
    LOCATE("LOCATE(", "instr(") {
      @Override
      public GenerationDataType[] inputTypes(GenerationDataType resultType) {
        return new GenerationDataType[]{DataTypeGroup.STRING_GROUP, DataTypeGroup.STRING_GROUP, DataTypeGroup.INT_GROUP};
      }
    },

    SPACE("SPACE(", "rpad('', ") {
      @Override
      public GenerationDataType[] inputTypes(GenerationDataType resultType) {
        return new GenerationDataType[]{DataType.U_BYTE};
      }

      @Override
      public void register() {
        FunctionMap.register(this, DataTypeGroup.STRING_GROUP);
      }
    },;

    private final String[] ops;

    StringToIntOp(String... s) {
      ops = s;
    }

    @Override
    public void register() {
      FunctionMap.register(this, DataTypeGroup.UINT_GROUP);
    }

    @Override
    public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
      for (Dialect dialect : dialects) {
        final StringBuilder builder = input[0].sql(dialect).insert(0, ops[dialect.ordinal()]);
        for (int i = 1; i < input.length; i++) {
          builder.append(Function.PARAMETER_SPLIT).append(input[i].sql(dialect));
        }
        builder.append(Function.CLOSE_PAREN);
      }
      return input[0];
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.STRING_GROUP};
    }
  }

  public static Function[] combinedValues() {
    return ObjectArrays.concat(values(),
        ObjectArrays.concat(VarStringOp.values(), StringToIntOp.values(), Function.class), Function.class);
  }
}
