package io.transwarp.db_specific.base;

import io.transwarp.db_specific.ANSIType;
import io.transwarp.db_specific.InceptorType;
import io.transwarp.db_specific.OracleType;
import io.transwarp.generate.type.*;

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

  public DBType getOriginType(GenerationDataType mapped) {
    assert mapped != DataType.BOOL;
    if (hasLen(mapped)) {
      for (DBType value : values) {
        if (value.mapToGeneration(DBType.LEN).equals(mapped)) {
          return value;
        }
      }
    } else {
      for (DBType value : values) {
        try {
          if (value.mapToGeneration(DBType.NO_LEN) == mapped) {
            return value;
          }
        } catch (Exception ignored) {}
      }
    }
    if (DataTypeGroup.UINT_GROUP.contains(mapped) || mapped == DataType.BYTE || mapped.equals(SequenceDataType.BITS)) {
      return ANSIType.INT;
    }
    throw new IllegalArgumentException("unknown mapping: " + mapped);
  }

  private boolean hasLen(GenerationDataType mapped) {
    return mapped instanceof CompoundDataType;
  }
}
