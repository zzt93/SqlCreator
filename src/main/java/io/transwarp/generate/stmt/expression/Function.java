package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.config.expr.ExprConfig;
import io.transwarp.generate.config.expr.InputRelation;
import io.transwarp.generate.config.expr.UdfFilter;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3>how to handle function overloading:</h3>
 * <li>return input type according to result type</li>
 * <li>for function with default arguments, implement multiple times</li>
 * <li>for functions with variable args,
 * mark last input type to be variable length: {@link StringOp.VarStringOp#inputTypes(GenerationDataType)}</li>
 * <p>
 * <h3>how to handle input relation</h3>
 * <li>{@link InputRelation}</li>
 * <li>{@link ArithOp} & {@link ConversionOp} specify in the {@link #inputTypes(GenerationDataType)}</li>
 * <li>{@link DateOp#DATE_STRING_AND_PATTERN} & {@link DateOp#TO_DATE}: generate related input at the same time</li>
 * <li>Not addressed: printf</li>
 * <p>
 * <h3>work flow</h3>
 * <li>result type (specific type is more easy to be found;
 * return shorter/smaller type, can register longer/larger type: e.g return short -- register int longer than short)</li>
 * <li>apply function</li>
 * <li>input types (more choice is better -- so prefer group; different types is better)</li>
 *
 * @see FunctionMap#random(GenerationDataType, UdfFilter)
 * @see InputRelation#refine(GenerationDataType[])
 * @see Operand#makeOperand(GenerationDataType, ExprConfig, int, UdfFilter)
 */
public interface Function {

  char CLOSE_PAREN = ')';
  char OPEN_PAREN = '(';
  String PARAMETER_SPLIT = ", ";

  /**
   * register it exact return type and larger group/longer type,
   * i.e. up-cast type {@link io.transwarp.generate.type.DataTypeGroup#upCast(GenerationDataType)}.
   * And now, it is normally register upCast type by hand for some type should not cast
   * -- {@link io.transwarp.generate.type.DataType.Internal}.
   *
   * @see io.transwarp.generate.type.DataTypeGroup
   * @see FunctionMap#register(Function, GenerationDataType)
   */
  void register();

  /**
   * <li> update sql</li>
   * <li> update type -- set at {@link Operand#makeOperand(GenerationDataType, ExprConfig, int, UdfFilter)}
   *
   * @param dialects used when different dialect has different ways to apply
   * @param input   parameter  @return result
   * @see ParenWrapper
   * @see GenerationDataType#randomData(Dialect[]) -- generation should in one method call
   * @see io.transwarp.generate.common.Column#getNameOrConst(Dialect[], BiChoicePossibility) -- generation put in one call
   */
  Operand apply(Dialect[] dialects, GenerationDataType resultType, Operand... input);

  /**
   * TODO may change return type to add the restriction between inputs, i.e. specify input relation for each function
   * <li>sometimes, the result type depend on input type, like {@link ArithOp},
   * by using the parameter, we can avoid overloading manually</li>
   * <li>input type can be set as large as possible, so we could generate more choice, more exact type
   * to use, i.e. generate more function overloading -- down-cast type</li>
   * <li>the process to down cast type is finished by {@link InputRelation}, or
   * by itself by using {@link io.transwarp.generate.type.DataTypeGroup#randomDownCast(GenerationDataType)}
   * or {@link io.transwarp.generate.type.DataTypeGroup#numRandDownCast(GenerationDataType)}</li>
   *
   * @return the input types that can produce result type
   * @see ArithOp
   * @see InputRelation
   * @see io.transwarp.generate.type.DataTypeGroup
   */
  GenerationDataType[] inputTypes(GenerationDataType resultType);
}
