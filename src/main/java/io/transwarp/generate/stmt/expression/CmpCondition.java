package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.common.Table;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 * <br>states</br>
 * <br>operation</br>
 * <br>result</br>
 */
public class CmpCondition extends Condition {
  private StringBuilder stringBuilder;

  public CmpCondition(Table from) {
    CmpOp op = CmpOp.randomOp();
    stringBuilder = new StringBuilder().append(op.sql(from));
  }

  public StringBuilder sql() {
    return stringBuilder;
  }

}
