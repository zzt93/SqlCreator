package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 1/5/17.
 * <p>
 * <h3></h3>
 */
public enum  AggregateOp implements Function{
  ;

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
}
