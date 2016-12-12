package io.transwarp.generate.common;

import com.google.common.base.Optional;
import io.transwarp.generate.SqlGeneration;
import io.transwarp.generate.stmt.expression.Condition;

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

}
