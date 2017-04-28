package io.transwarp.db_specific;

import com.google.common.base.Preconditions;
import io.transwarp.db_specific.base.DBType;
import io.transwarp.generate.config.FixedParams;
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
      if (len == NO_LEN) {
        len = FixedParams.getRandomStrMaxLen();
      }
      return SequenceDataType.sequence(DataType.Meta.CHAR, len);
    }
  },
  VARCHAR {
    @Override
    public String getName() {
      return addLen(LEN);
    }

    @Override
    public GenerationDataType mapToGeneration(int len) {
      Preconditions.checkArgument(len > 0, "invalid len for varchar: " + len);
      return SequenceDataType.sequence(DataType.Meta.UNICODE, len);
    }
  },
  DATE {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.UNIX_DATE;
    }
  },
  TIMESTAMP {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.TIMESTAMP;
    }
  },
  BINARY {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.BINARY;
    }
  }
  ;

  String addLen(int len) {
    return name() + '(' + len + ')';
  }

  @Override
  public String getName() {
    return name();
  }
}
