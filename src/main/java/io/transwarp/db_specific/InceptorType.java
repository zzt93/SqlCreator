package io.transwarp.db_specific;

import io.transwarp.db_specific.base.DBType;
import io.transwarp.generate.type.SequenceDataType;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public enum InceptorType implements DBType {
  STRING {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return new SequenceDataType(DataType.CHAR, len);
    }
  };
}
