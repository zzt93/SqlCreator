package io.transwarp.generate.stmt.select;

import io.transwarp.DDLParserTest;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.Config;
import io.transwarp.generate.config.InputRelation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by zzt on 12/12/16.
 * <p>
 *   java -cp /usr/lib/qa/player-1.0-all.jar io.transwarp.qa.player.SuiteRunner -t case.yml -C conf.xml
 * <h3></h3>
 */
@RunWith(Parameterized.class)
public class SelectResultTest {

  private int count = 100;
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
    PrintWriter oracle  = new PrintWriter(new OutputStreamWriter(new FileOutputStream("oracle")));
    PrintWriter inceptor  = new PrintWriter(new OutputStreamWriter(new FileOutputStream("inceptor")));
    for (SelectResult selectResult : selectResults) {
      oracle.println(selectResult.sql(Config.getBase()));
      inceptor.println(selectResult.sql(Config.getCmp()));
    }
  }

}