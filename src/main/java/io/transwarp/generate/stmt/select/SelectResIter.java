package io.transwarp.generate.stmt.select;

import io.transwarp.db_specific.base.Dialect;

import java.util.ArrayList;

/**
 * Created by zzt on 12/30/16.
 * <p>
 * <h3></h3>
 */
public class SelectResIter {
  private int index = 0;
  private ArrayList<SelectResult> results = new ArrayList<>();
  private int subQueryDepth;

  public SelectResIter(int subQueryDepth) {
    this.subQueryDepth = subQueryDepth;
  }

  public String next(int colLimit, Dialect dialect) {
    if (index >= results.size()) {
      final SelectResult result = SelectResult.simpleQuery(colLimit, subQueryDepth);
      results.add(result);
    }
    assert index < results.size();
    return results.get(index++).sql(dialect).toString();
  }

  public void reset() {
    index = 0;
  }
}
