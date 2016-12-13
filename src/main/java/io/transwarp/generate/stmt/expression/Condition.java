package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.ParenAspect;

/**
 * Created by zzt on 12/5/16.
 * <p>
 * <h3></h3>
 * <a href="https://docs.oracle.com/cd/B28359_01/server.111/b28286/conditions001.htm#SQLRF52081">document of
 * oracle condition</a>
 */
public abstract class Condition implements SqlGeneration {

  /**
   * @return return the string represent the condition of sql
   *
   * @see ParenAspect
   * Post condition: -- ensured by aop
   * assert(sb.charAt(0) == '(');
   * assert(sb.charAt(sb.length()-1) == ')');
   */
  public abstract StringBuilder sql();

}
