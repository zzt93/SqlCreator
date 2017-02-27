package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 1/5/17.
 * <p>
 * <h3></h3>
 */
public enum AggregateOp implements Function {
  COUNT,
  SUM {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.numRandDownCast(resultType)};
    }
  },
  AVG {
    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return new GenerationDataType[]{DataTypeGroup.numRandDownCast(resultType)};
    }
  },
  MIN, MAX;

  @Override
  public void register() {
    FunctionMap.register(this, DataTypeGroup.NUM_GROUP);
  }

  @Override
  public Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input) {
    for (Dialect dialect : dialects) {
      input[0].sql(dialect)
          .insert(0, name() + OPEN_PAREN).append(CLOSE_PAREN);
    }
    return input[0];
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return new GenerationDataType[]{DataTypeGroup.ALL_BUT_BOOL_BINARY_LIST};
  }
}
