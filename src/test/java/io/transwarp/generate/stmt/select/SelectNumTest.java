package io.transwarp.generate.stmt.select;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.op.SelectConfig;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.parse.sql.DDLParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by zzt on 2/9/17.
 * <p>
 * <h3></h3>
 */
public class SelectNumTest {
  private List<Table> candidates;

  public SelectNumTest() {
    candidates = DDLParser.getTable("default_oracle.sql", Dialect.ORACLE);
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
}
