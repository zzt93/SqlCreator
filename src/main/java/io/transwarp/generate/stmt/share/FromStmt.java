package io.transwarp.generate.stmt.share;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.PerGenerationConfig;
import io.transwarp.generate.stmt.ContainSubQuery;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class FromStmt implements SqlGeneration, ContainSubQuery {

  private static final String FROM = " from ";
  private Table fromObj;

  public FromStmt(Table fromObj, PerGenerationConfig queryDepth) {
    this.fromObj = fromObj;
  }

  @Override
  public StringBuilder sql(Dialect dialect) {
    return new StringBuilder(FROM).append(fromObj.sql(dialect));
  }

  @Override
  public void replaceWithSimpleQuery(int subQueryDepth) {

  }
}
