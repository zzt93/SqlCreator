package io.transwarp.generate.common;

import com.google.common.base.Optional;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.stmt.share.Condition;

import java.util.ArrayList;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public interface Table extends SqlGeneration {

  Table join(Table table, Condition condition);

  Optional<String> name();

  ArrayList<Column> columns();

  Table setAlias(String alias);

  /**
   * change name and columns, to produce a new table for use
   * @param alias new name of this table
   * @return a deeply copied table, not share info with original one
   */
  Table toTable(String alias);

  /*
  ---------- sql generation --------------
   */
  StringBuilder toTableSql(Dialect dialect);

}
