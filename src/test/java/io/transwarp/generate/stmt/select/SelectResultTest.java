package io.transwarp.generate.stmt.select;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.parse.sql.DDLParser;
import org.junit.Test;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class SelectResultTest {
  @Test
  public void toSql() throws Exception {
    final DDLParser ddlParser = new DDLParser("src/test/test.sql", Dialect.ORACLE);
    final Table parse = ddlParser.parse();
  }

}