package io.transwarp.db_specific;

import io.transwarp.db_specific.base.Dialect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zzt on 2/24/17.
 * <p>
 * <h3></h3>
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DialectSpecific {
  Dialect value() default Dialect.INCEPTOR;
  String desc() default "";
}
