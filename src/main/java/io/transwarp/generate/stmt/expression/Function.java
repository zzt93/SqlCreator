package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public interface Function {

  void register();

  Operand apply(Operand... input);

  GenerationDataType[] inputTypes(GenerationDataType resultType);
}
