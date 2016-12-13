package io.transwarp.generate.stmt.select;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.Config;
import io.transwarp.parse.sql.DDLParser;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class SelectResultTest {

  private int count = 1000;
  private SelectResult[] selectResults;
  private Table from;

  @Before
  public void setUp() throws Exception {
    final DDLParser ddlParser = new DDLParser("src/test/resources/test.sql", Dialect.ORACLE);
    from = ddlParser.parse();
    selectResults = new SelectResult[count];
    for (int i = 0; i < selectResults.length; i++) {
      selectResults[i] = new SelectResult(Config.getSubQueryDepth(), from);
    }
  }

  @Test
  public void name() throws Exception {
    for (SelectResult selectResult : selectResults) {
      assert !selectResult.name().isPresent();
    }
  }

  @Test
  public void columns() throws Exception {
    for (SelectResult selectResult : selectResults) {
      assert selectResult.columns().size() <= from.columns().size();
    }
  }

  @Test
  public void sql() throws Exception {
    for (SelectResult selectResult : selectResults) {
      System.out.println(selectResult.sql());
    }
  }

}