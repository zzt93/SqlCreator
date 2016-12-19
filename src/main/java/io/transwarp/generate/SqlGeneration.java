package io.transwarp.generate;

import io.transwarp.db_specific.base.Dialect;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public interface SqlGeneration {

  /**
   * this function should have no function side-effect, for it will be invoked multiple times
   * @param dialect sql dialect
   * @return sql representation of this part
   */
  StringBuilder sql(Dialect dialect);

}
