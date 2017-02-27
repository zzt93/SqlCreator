package io.transwarp.parse.sql;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import org.junit.Test;

import java.util.List;

/**
 * Created by zzt on 2/16/17.
 * <p>
 * <h3></h3>
 */
public class DDLParserTest {

  @Test
  public void testInceptor() throws Exception {
    delimiterTest("default_inceptor.sql", Dialect.INCEPTOR);
  }

  @Test
  public void testOracle() throws Exception {
    delimiterTest("default_oracle.sql", Dialect.ORACLE);
  }

  private void delimiterTest(String file, Dialect dialect) throws Exception {
    final Table parse = DDLParser.getTable(file, dialect).get(0);

    assert parse.name().isPresent();
    assert parse.name().get().equals("test_udf");
    assert parse.columns().size() == 13;
  }

  @Test
  public void testView() throws Exception {
    final List<Table> tables = DDLParser.getTable("default_inceptor.sql", Dialect.INCEPTOR);
    final Table parse = tables.get(2);

    assert parse.name().isPresent();
    assert parse.name().get().equals("test_view");
    assert parse.columns().size() == 13;
  }
}