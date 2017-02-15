package io.transwarp.generate.stmt.select;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.parse.xml.ConfigUnmarshallerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
  private List<Table> fromObj;

  public SelectResultTest(QueryConfig config) throws Exception {
    this.queryConfig = config;
    setUp();
  }

//  @Parameterized.Parameters
//  public static InputRelation[] data() {
//    return InputRelation.values();
//    return new InputRelation[]{InputRelation.SAME};
//  }

  @Parameterized.Parameters
  public static QueryConfig[] data() throws Exception {
    final GlobalConfig parse = ConfigUnmarshallerTest.getGlobalConfig();
    List<QueryConfig> list = parse.getQueries();
    return list.toArray(new QueryConfig[0]);
  }

  public void setUp() throws Exception {
    System.err.println(queryConfig.getId());
    fromObj = queryConfig.getFrom().getFromObj();
    selectResults = new SelectResult[count];
    for (int i = 0; i < count; i++) {
      selectResults[i] = SelectResult.generateQuery(queryConfig);
    }
    oracle = new PrintWriter(new OutputStreamWriter(new FileOutputStream("oracle/" + queryConfig.getId() + ".sql", false)));
    inceptor = new PrintWriter(new OutputStreamWriter(new FileOutputStream("inceptor/" + queryConfig.getId() + ".sql", false)));
  }


  public void name() throws Exception {
    for (SelectResult selectResult : selectResults) {
      assert !selectResult.name().isPresent();
    }
  }

  public void columns() throws Exception {
    for (SelectResult selectResult : selectResults) {
      assert selectResult.columns().size() <= TableUtil.columns(fromObj).size();
    }
  }

  @Test
  public void sql() throws Exception {
    name();
    columns();
    for (SelectResult selectResult : selectResults) {
      oracle.println(selectResult.sql(GlobalConfig.getBase()));
      inceptor.println(selectResult.sql(GlobalConfig.getCmp()));
    }
    close();
  }

  public void close() {
    oracle.close();
    inceptor.close();
  }
}