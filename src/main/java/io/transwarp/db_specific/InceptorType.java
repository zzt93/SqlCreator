package io.transwarp.db_specific;

import io.transwarp.db_specific.base.DBType;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.generate.type.SequenceDataType;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public enum InceptorType implements DBType {

  TINYINT {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.BYTE;
    }
  },
  SMALLINT {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.SHORT;
    }
  },
  INT {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.INT;
    }
  },
  BIGINT {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.LONG;
    }
  },
  FLOAT {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.FLOAT;
    }
  },
  DOUBLE {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.DOUBLE;
    }
  },
  DECIMAL {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.DECIMAL;
    }
  },
  BOOLEAN {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.BOOL;
    }
  },
  STRING {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return SequenceDataType.sequence(DataType.Meta.CHAR, len);
    }
  },
  VARCHAR {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return SequenceDataType.sequence(DataType.Meta.UNICODE, len);
    }
  },
  DATE {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.DATE;
    }
  },
  TIMESTAMP {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.TIMESTAMP;
    }
  },
  ;
}
