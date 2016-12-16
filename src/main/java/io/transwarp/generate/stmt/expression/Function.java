package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public interface Function {

  void register();

  /**
   * <li> update sql</li>
   * <li> update type -- implement by aop</li>
   * @param input parameter
   * @return result
   * @see io.transwarp.generate.common.FunctionApplyAspect
   */
  Operand apply(Operand... input);

  GenerationDataType[] inputTypes(GenerationDataType resultType);
}
