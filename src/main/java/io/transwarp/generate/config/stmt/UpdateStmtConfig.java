package io.transwarp.generate.config.stmt;

import io.transwarp.db_specific.base.Dialect;

import java.util.EnumMap;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class UpdateStmtConfig extends StmtConfig {
  @Override
  public EnumMap<Dialect, String> generate(Dialect[] dialects) {
    // TODO 2/8/17 impl
    EnumMap<Dialect, String> res = new EnumMap<Dialect, String>(Dialect.class);
    return res;
  }
}
