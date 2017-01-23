package io.transwarp.generate.stmt.select;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.config.expr.InputRelation;
import io.transwarp.generate.config.op.SelectConfig;
import io.transwarp.generate.config.stmt.QueryConfig;
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
  private QueryConfig queryConfig;

  public SelectResultTest(InputRelation relation) {
    queryConfig = new QueryConfig();
  }

  @Parameterized.Parameters
  public static InputRelation[] data() {
//    return InputRelation.values();
    return new InputRelation[]{InputRelation.SAME};
  }

  @Before
  public void setUp() throws Exception {
    final Table[] table = DDLParser.getTable("src/main/resources/default_oracle.sql", Dialect.ORACLE);
    from = table[0];
    selectResults = new SelectResult[count];
    for (int i = 0; i < selectResults.length; i++) {
      selectResults[i] = SelectResult.selectResult(queryConfig, table);
    }
    oracle = new PrintWriter(new OutputStreamWriter(new FileOutputStream("o.sql", true)));
    inceptor = new PrintWriter(new OutputStreamWriter(new FileOutputStream("i.sql", true)));
  }

  @Test
  public void selectResult() throws Exception {
    QueryConfig simpleQuery = new QueryConfig();
    final SelectConfig select = new SelectConfig();
    simpleQuery.setSelect(select);
    for (int i = 1; i < 10; i++) {
      select.setSelectNum(i);
      final SelectResult selectResult = SelectResult.simpleQuery(simpleQuery);
      final int size = selectResult.columns().size();
      Assert.assertTrue(size > 0 && size <= i);
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
      oracle.println(selectResult.sql(GlobalConfig.getBase()));
      inceptor.println(selectResult.sql(GlobalConfig.getCmp()));
    }
  }

  @After
  public void close() {
    oracle.close();
    inceptor.close();
  }
}