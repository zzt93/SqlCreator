package io.transwarp.generate.config.stmt;

import io.transwarp.db_specific.base.Dialect;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class UpdateStmtConfig extends StmtConfig {
  @Override
  public String[] generate(Dialect[] dialects) {
    final String[] res = new String[dialects.length];
    // TODO 2/8/17 impl
    return res;
  }
}
