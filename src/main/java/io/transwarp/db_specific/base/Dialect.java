package io.transwarp.db_specific.base;

import io.transwarp.db_specific.InceptorType;
import io.transwarp.db_specific.OracleType;

/**
 * Created by zzt on 12/7/16.
 * <p>
 * <h3></h3>
 *
 * <p>The order of following dialect determines the choice of string representation of date operator</p>
 * @see io.transwarp.generate.stmt.expression.DateOp
 */
public enum Dialect {

  INCEPTOR(InceptorType.values()),
  ORACLE(OracleType.values()),;

  private final DBType[] values;

  Dialect(DBType[] values) {
    this.values = values;
  }

  public DBType getType(String name) {
    for (DBType value : values) {
      if (name.startsWith(value.toString())) {
        return value;
      }
    }
    throw new IllegalArgumentException("unknown name: " + name);
  }
}
