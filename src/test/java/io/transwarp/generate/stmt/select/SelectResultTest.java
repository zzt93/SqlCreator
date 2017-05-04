package io.transwarp.generate.stmt.select;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.TestsConfig;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.util.Strs;
import io.transwarp.parse.cl.CLParser;
import io.transwarp.parse.xml.ConfigUnmarshaller;
import io.transwarp.parse.xml.XMLParserSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
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
  private SelectResult[] selectResults;
  private List<Table> fromObj;
  private static CLParser clParser;

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
    ConfigUnmarshaller configUnmarshaller = new ConfigUnmarshaller();
    clParser = new CLParser(Strs.of(ClassLoader.getSystemResource("test.xml").getFile(), "oracle=oracle", "inceptor=inceptor"));
    final TestsConfig parse = configUnmarshaller.parse(new XMLParserSource(clParser.getInputPath()));
    List<QueryConfig> list = parse.getQueries();
    return list.toArray(new QueryConfig[0]);
  }

  public void setUp() throws Exception {
    System.err.println(queryConfig.getId());
    int count = queryConfig.getNum();
    selectResults = new SelectResult[count];
    for (int i = 0; i < count; i++) {
      selectResults[i] = SelectResult.generateQuery(queryConfig);
    }
    fromObj = queryConfig.getFrom().getFromObj();
  }

  private PrintWriter getWriter(Path dir, String prefix) throws FileNotFoundException {
    final Path path = Paths.get(dir.toString(), prefix + ".sql");
    return new PrintWriter(new OutputStreamWriter(
        new FileOutputStream(path.toString(), false)));
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
      System.out.println(selectResult.sql(TestsConfig.getBase()));
      System.out.println(selectResult.sql(TestsConfig.getCmp()));
    }
  }

}