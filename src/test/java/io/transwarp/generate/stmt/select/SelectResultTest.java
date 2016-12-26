package io.transwarp.generate.stmt.select;

import io.transwarp.DDLParserTest;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.Config;
import io.transwarp.generate.config.InputRelation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
@RunWith(Parameterized.class)
public class SelectResultTest {

  private int count = 1000;
  private SelectResult[] selectResults;
  private Table from;

  public SelectResultTest(InputRelation relation) {
    new Config.Builder().setInputRelation(relation).build();
  }

  @Parameterized.Parameters
  public static InputRelation[] data() {
    return InputRelation.values();
  }

  @Before
  public void setUp() throws Exception {
    from = DDLParserTest.getTable();
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
      assert selectResult.columns().size() <= from.columns().size() + Config.getExprNumInSelect();
    }
  }

  @Test
  public void sql() throws Exception {
    for (SelectResult selectResult : selectResults) {
      System.out.println(selectResult.sql(Config.getBase()));
      System.out.println(selectResult.sql(Config.getCmp()));
    }
  }

}