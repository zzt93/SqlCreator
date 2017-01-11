package io.transwarp.db_specific.base;

import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/7/16.
 * <p>
 * <h3></h3>
 */
public interface DBType {

  int NO_LEN = -1;
  int LEN = 100;

  GenerationDataType mapToGeneration(int len);
  String getName();
}
