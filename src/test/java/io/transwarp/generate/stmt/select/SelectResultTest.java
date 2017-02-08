package io.transwarp.generate.stmt.select;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.config.PerTestConfig;
import io.transwarp.generate.config.op.SelectConfig;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.config.stmt.StmtConfig;
import io.transwarp.parse.sql.DDLParser;
import io.transwarp.parse.xml.ConfigUnmarshallerTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * java -cp /usr/lib/qa/player-1.0-all.jar io.transwarp.qa.player.SuiteRunner -t case.yml -C conf.xml
 * <h3></h3>
 */
@RunWith(Parameterized.class)
public class SelectResultTest {

  private final QueryConfig queryConfig;
  private int count = 10;
  private SelectResult[] selectResults;
  private PrintWriter oracle;
  private PrintWriter inceptor;
  private List<Table> candidates;
  private List<Table> fromObj;

  public SelectResultTest(QueryConfig config) {
    this.queryConfig = config;
  }

//  @Parameterized.Parameters
//  public static InputRelation[] data() {
//    return InputRelation.values();
//    return new InputRelation[]{InputRelation.SAME};
//  }

  @Parameterized.Parameters
  public static QueryConfig[] data() throws Exception {
    final GlobalConfig parse = ConfigUnmarshallerTest.getGlobalConfig();
    List<QueryConfig> list = new ArrayList<>();
    for (PerTestConfig perTestConfig : parse.getPerTestConfigs()) {
      for (StmtConfig stmtConfig : perTestConfig.getStmtConfigs()) {
        if (stmtConfig instanceof QueryConfig) {
          list.add((QueryConfig) stmtConfig);
        }
      }
    }
    return list.toArray(new QueryConfig[0]);
  }

  @Before
  public void setUp() throws Exception {
    candidates = DDLParser.getTable("default_oracle.sql", Dialect.ORACLE);
    fromObj = queryConfig.getFrom().getFromObj();
    selectResults = new SelectResult[count];
    for (int i = 0; i < count; i++) {
      selectResults[i] = SelectResult.generateQuery(queryConfig);
    }
    oracle = new PrintWriter(new OutputStreamWriter(new FileOutputStream(queryConfig.getId() + ".o.sql", true)));
    inceptor = new PrintWriter(new OutputStreamWriter(new FileOutputStream(queryConfig.getId() + ".i.sql", true)));
  }

  @Test
  public void selectResult() throws Exception {
    QueryConfig simpleQuery = QueryConfig.simpleQuery(candidates);
    final SelectConfig select = new SelectConfig();
    simpleQuery.setSelect(select);
    for (int i = 1; i < 10; i++) {
      select.setSelectNum(i);
      final SelectResult selectResult = SelectResult.generateQuery(simpleQuery);
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
      assert selectResult.columns().size() <= TableUtil.columns(fromObj).size();
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