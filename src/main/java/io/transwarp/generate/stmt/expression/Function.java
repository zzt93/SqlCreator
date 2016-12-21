package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3>how to handle function overloading:</h3>
 * <li>return input type according to result type</li>
 * <li>for function with default arguments, implement multiple times</li>
 * <li>for functions with variable args, mark last input type to be variable length</li>
 */
public interface Function {

  char CLOSE_PAREN = ')';

  void register();

  /**
   * <li> update sql</li>
   * <li> update type -- set at {@link Operand#makeOperand(GenerationDataType, Table, int)}</li>
   *
   * @param dialect used when different dialect has different ways to apply
   * @param input   parameter
   * @return result
   * @see io.transwarp.generate.stmt.expression.FunctionMap.FunctionWrapper
   */
  Operand apply(Dialect dialect, Operand... input);

  /**
   * sometimes, the result type depend on input type, like {@link ArithOp},
   * by using the parameter, we can avoid overloading manually
   *
   * @return the input types that can produce result type
   * @see ArithOp
   */
  GenerationDataType[] inputTypes(GenerationDataType resultType);
}
