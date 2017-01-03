package io.transwarp.generate.stmt.share;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.PerGenerationConfig;
import io.transwarp.generate.stmt.ContainSubQuery;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class WhereStmt implements SqlGeneration, ContainSubQuery {


  private static final String WHERE = " where ";
  private final Condition condition;

  public WhereStmt(Table from, PerGenerationConfig config) {
    condition = new Condition(from, config);
    if (config.hasSubQuery()) {
      replaceWithSimpleQuery(config.getQueryDepth());
    }
  }

  @Override
  public StringBuilder sql(Dialect dialect) {
    return new StringBuilder(WHERE).append(condition.sql(dialect));
  }

  @Override
  public void replaceWithSimpleQuery(int queryDepth) {
    condition.replaceWithSimpleQuery(queryDepth);
  }
}
