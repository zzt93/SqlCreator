package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/20/16.
 * <p>
 * <h3></h3>
 */
public enum ConversionOp implements Function{
  CAST,
  BINARY;


  @Override
  public void register() {

  }

  @Override
  public Operand apply(Dialect dialect, Operand... input) {
    return null;
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return new GenerationDataType[0];
  }
}
