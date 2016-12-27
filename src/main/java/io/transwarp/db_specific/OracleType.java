package io.transwarp.db_specific;

import io.transwarp.db_specific.base.DBType;
import io.transwarp.generate.type.SequenceDataType;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/7/16.
 * <p>
 * <h3></h3>
 * <a href="https://docs.oracle.com/cd/B19306_01/server.102/b14200/sql_elements001.htm#i54330">oracle data types</a>
 */
public enum OracleType implements DBType {


  /**
   * type for varchar & varchar2
   */
  VARCHAR {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      if (len > 4000 || len == NO_LEN) {
        throw new IllegalArgumentException("invalid len for varchar2: " + len);
      }
      return SequenceDataType.sequence(DataType.Meta.CHAR, len);
    }
  },
  NVARCHAR {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      if (len > 4000 || len == NO_LEN) {
        throw new IllegalArgumentException("invalid len for nvarchar2: " + len);
      }
      return SequenceDataType.sequence(DataType.Meta.UNICODE, len);
    }
  },
  NUMBER {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      if (len != NO_LEN) {
        return SequenceDataType.sequence(DataType.Meta.BIT, len);
      }
      // TODO add precision and scale
      return DataType.DECIMAL;
    }
  },
  LONG {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.LONG;
    }
  },
  DATE {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.DATE;
    }
  },
  BINARY_FLOAT {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.FLOAT;
    }
  },
  BINARY_DOUBLE {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.DOUBLE;
    }
  },
  TIMESTAMP {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.TIMESTAMP;
    }
  },
  CHAR {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      if (len > 2000) {
        throw new IllegalArgumentException("too long for varchar2: " + len);
      }
      if (len == DBType.NO_LEN) {
        len = 1;
      }
      return SequenceDataType.sequence(DataType.Meta.CHAR, len);
    }
  },
  NCHAR {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      if (len > 2000) {
        throw new IllegalArgumentException("too long for varchar2: " + len);
      }
      if (len == DBType.NO_LEN) {
        len = 1;
      }
      return SequenceDataType.sequence(DataType.Meta.UNICODE, len);
    }
  },


  /**
   * ----------------------------------------------------------------------
   * ----------the following is the ansi type that oracle support---------
   */
  SMALLINT {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.SHORT;
    }
  },
  INT {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return ANSIType.INT.mapToGeneration(len);
    }
  },;

}
