package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
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
   *
   * @param dialect used when different dialect has different ways to apply
   * @param input parameter
   * @return result
   * @see io.transwarp.generate.common.FunctionApplyAspect
   */
  Operand apply(Dialect dialect, Operand... input);

  GenerationDataType[] inputTypes(GenerationDataType resultType);
}
