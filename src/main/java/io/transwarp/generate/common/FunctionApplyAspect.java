package io.transwarp.generate.common;

import io.transwarp.generate.stmt.expression.Function;
import io.transwarp.generate.stmt.expression.FunctionMap;
import io.transwarp.generate.stmt.expression.Operand;
import io.transwarp.generate.type.GenerationDataType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by zzt on 12/16/16.
 * <p>
 * <h3></h3>
 */
@Aspect
public class FunctionApplyAspect {


  @AfterReturning(
          pointcut = "io.transwarp.generate.stmt.expression.Function+.apply()",
          returning = "operand")
  public void doAccessCheck(JoinPoint jp, Object operand) {
    final GenerationDataType type = FunctionMap.resultType((Function) jp.getThis());
    ((Operand) operand).setType(type);
  }
}
