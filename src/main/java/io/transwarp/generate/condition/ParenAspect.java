package io.transwarp.generate.condition;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by zzt on 12/8/16.
 * <p>
 * <h3></h3>
 */
@Aspect
public class ParenAspect {

    @Around("execution(* io.transwarp.generate.condition.Condition+.toSql(..))")
    public Object addParen(ProceedingJoinPoint pjp) {
        final StringBuilder proceed;
        try {
            proceed = (StringBuilder) pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return new StringBuilder();
        }
        proceed.insert(0, '(');
        proceed.append(')');
        return proceed;
    }
}
