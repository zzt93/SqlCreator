package io.transwarp.db_specific.base;

import io.transwarp.db_specific.ANSIType;
import io.transwarp.db_specific.InceptorType;
import io.transwarp.db_specific.OracleType;
import io.transwarp.generate.type.*;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Created by zzt on 12/7/16.
 * <p>
 * <h3></h3>
 *
 * <p>The order of following dialect determines the choice of string representation of date operator</p>
 * @see io.transwarp.generate.stmt.expression.DateOp
 */
@XmlEnum
public enum Dialect {

  INCEPTOR(InceptorType.values()) {
    @Override
    public DBType getOriginType(GenerationDataType mapped) {
      if (mapped == DataType.LONG) {
        return InceptorType.BIGINT;
      }
      return super.getOriginType(mapped);
    }
  },
  ORACLE(OracleType.values()) {
    @Override
    public DBType getOriginType(GenerationDataType mapped) {
      if (mapped == DataType.LONG) {
        return OracleType.NUMBER;
      }
      return super.getOriginType(mapped);
    }
  },;

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
    if (noMappingBackInt(mapped)) {
      return ANSIType.INT;
    }
    throw new IllegalArgumentException("unknown mapping: " + mapped);
  }

  public static boolean noMappingBackInt(GenerationDataType mapped) {
    return DataTypeGroup.UINT_GROUP.contains(mapped)
        || mapped == DataType.BYTE
        || mapped.equals(SequenceDataType.BITS);
  }

  private boolean hasLen(GenerationDataType mapped) {
    return mapped instanceof CompoundDataType;
  }
}
