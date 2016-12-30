package io.transwarp;

import io.transwarp.generate.common.Table;
import io.transwarp.parse.sql.DDLParser;
import org.junit.Test;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 */
public class DDLParserTest {
  @Test
  public void delimiterTest() throws Exception {
    final Table parse = DDLParser.getTable()[0];
    assert parse.name().isPresent();
    assert parse.name().get().equals("test_udf");
    assert parse.columns().size() == 13;
  }


}