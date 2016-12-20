package io.transwarp;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.parse.sql.DDLParser;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 */
public class DDLParserTest {
  @Test
  public void delimiterTest() throws Exception {
    final Table parse = getTable();
    assert parse.name().isPresent();
    assert parse.name().get().equals("test");
    assert parse.columns().size() == 5;
  }

  public static Table getTable() throws IOException {
    final DDLParser ddlParser = new DDLParser("src/test/resources/test.sql", Dialect.ORACLE);
    return ddlParser.parse();
  }

}