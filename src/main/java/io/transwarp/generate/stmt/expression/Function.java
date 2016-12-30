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
 * <p>
 * <h3>work flow</h3>
 * <li>result type (specific type is more easy to be found)</li>
 * <li>function</li>
 * <li>input types (more choice is better; different types is better)</li>
 *
 * @see FunctionMap#random(GenerationDataType, UDFChooseOption)
 * @see io.transwarp.generate.config.InputRelation#refine(GenerationDataType[])
 * @see Operand#makeOperand(GenerationDataType, Table, int)
 */
public interface Function {

  char CLOSE_PAREN = ')';
  String PARAMETER_SPLIT = ", ";

  void register();

  /**
   * <li> update sql</li>
   * <li> update type -- set at {@link Operand#makeOperand(GenerationDataType, Table, int)}</li>
   *
   * @param dialect used when different dialect has different ways to apply
   * @param input   parameter
   * @return result
   * @see ParenWrapper
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
