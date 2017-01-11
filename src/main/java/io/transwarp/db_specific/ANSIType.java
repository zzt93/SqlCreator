package io.transwarp.db_specific;

import io.transwarp.db_specific.base.DBType;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.generate.type.SequenceDataType;

/**
 * Created by zzt on 12/8/16.
 * <p>
 * <h3></h3>
 */
public enum ANSIType implements DBType {

  DECIMAL {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      return DataType.DECIMAL;
    }
  },
  INT {
    @Override
    public GenerationDataType mapToGeneration(int len) {
      if (len == DBType.NO_LEN) {
        return DataType.INT;
      }
      return SequenceDataType.sequence(DataType.Meta.BIT, len);
    }
  };


  @Override
  public String getName() {
    return name();
  }
}
