package io.transwarp.generate.stmt.select;

import io.transwarp.db_specific.base.Dialect;

import java.util.ArrayList;

/**
 * Created by zzt on 12/30/16.
 * <p>
 * <h3></h3>
 */
public class QueryGenerator {
  private final int colLimit;
  private int index = 0;
  private final ArrayList<SelectResult> results = new ArrayList<>();
  private final int queryDepth;

  public QueryGenerator(int queryDepth, int colLimit) {
    this.queryDepth = queryDepth;
    this.colLimit = colLimit;
  }

  private String next(int colLimit, Dialect dialect) {
    if (index >= results.size()) {
      final SelectResult result = SelectResult.simpleQuery(colLimit, queryDepth);
      results.add(result);
    }
    assert index < results.size();
    return results.get(index++).sql(dialect).toString();
  }

  private void reset() {
    index = 0;
  }

  /**
   * replace all `aim` in `src` with query
   * @param src builder src
   * @param aim string aim
   */
  public void replaceAll(StringBuilder src, Dialect dialect, String aim) {
    int index = src.indexOf(aim);
    while (index != -1) {
      final String next = next(colLimit, dialect);
      src.replace(index, index + aim.length(), next);
      index += next.length(); // Move to the end of the replacement
      index = src.indexOf(aim, index);
    }
    reset();
  }

}
