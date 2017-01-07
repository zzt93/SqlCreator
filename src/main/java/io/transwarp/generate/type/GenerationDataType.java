package io.transwarp.generate.type;

import io.transwarp.db_specific.base.Dialect;

/**
 * Created by zzt on 12/7/16.
 * <p>
 * <h3></h3>
 */
public interface GenerationDataType {

  /**
   * <li>We used to think const is same for different dialect, but it proves not: {@link DataType#BOOL}</li>
   *
   * @param dialects dialects to differ the const
   * @return const value depend on dialect
   * @see DataType#BOOL
   * @see DataType#DATE_PATTERN
   * @see DataType#TIMESTAMP
   */
  String[] randomData(Dialect[] dialects);
// TODO String name(Dialect dialect);

  String getMax();

  String getMin();
}
