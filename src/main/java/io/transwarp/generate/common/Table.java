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

  StringBuilder toTable(Dialect dialect);

  Optional<String> name();

  ArrayList<Column> columns();

  Table setAlias(String alias);
}
