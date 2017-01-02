package io.transwarp.generate.stmt.select;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.Config;
import io.transwarp.generate.config.InputRelation;
import io.transwarp.parse.sql.DDLParser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * java -cp /usr/lib/qa/player-1.0-all.jar io.transwarp.qa.player.SuiteRunner -t case.yml -C conf.xml
 * <h3></h3>
 */
@RunWith(Parameterized.class)
public class SelectResultTest {

  private int count = 100;
  private SelectResult[] selectResults;
  private Table from;
  private PrintWriter oracle;
  private PrintWriter inceptor;

  public SelectResultTest(InputRelation relation) {
    new Config.Builder().setInputRelation(relation).build();
  }

  @Parameterized.Parameters
  public static InputRelation[] data() {
//    return InputRelation.values();
    return new InputRelation[]{InputRelation.SAME};
  }

  @Before
  public void setUp() throws Exception {
    from = DDLParser.getTable()[0];
    selectResults = new SelectResult[count];
    for (int i = 0; i < selectResults.length; i++) {
      selectResults[i] = SelectResult.selectResult();
    }
    oracle = new PrintWriter(new OutputStreamWriter(new FileOutputStream("o.sql", true)));
    inceptor = new PrintWriter(new OutputStreamWriter(new FileOutputStream("i.sql", true)));
  }

  @Test
  public void selectResult() throws Exception {
    for (int i = 1; i < 10; i++) {
      final SelectResult selectResult = SelectResult.simpleQuery(i, Config.getQueryDepth());
      Assert.assertTrue(selectResult.columns().size() <= i);
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
      oracle.println(selectResult.sql(Config.getBase()));
      inceptor.println(selectResult.sql(Config.getCmp()));
    }
  }

  @After
  public void close() {
    oracle.close();
    inceptor.close();
  }
}