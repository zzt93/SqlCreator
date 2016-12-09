package io.transwarp.generate.condition;

/**
 * Created by zzt on 12/5/16.
 * <p>
 * <h3></h3>
 * <a href="https://docs.oracle.com/cd/B28359_01/server.111/b28286/conditions001.htm#SQLRF52081">document of
 * oracle condition</a>
 */
abstract class Condition {

  Condition and(Condition condition) {
    toSql().append(LogicalOp.AND).append(condition.toSql());
    return this;
  }

  Condition or(Condition condition) {
    toSql().append(LogicalOp.OR).append(condition.toSql());
    return this;
  }

  Condition not() {
    toSql().insert(0, LogicalOp.NOT);
    return this;
  }

  /**
   * @return return the string represent the condition of sql
   *
   * @see ParenAspect
   * Post condition: -- ensured by aop
   * assert(sb.charAt(0) == '(');
   * assert(sb.charAt(sb.length()-1) == ')');
   */
  abstract StringBuilder toSql();

}
