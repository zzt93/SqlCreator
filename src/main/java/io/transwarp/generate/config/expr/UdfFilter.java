package io.transwarp.generate.config.expr;

import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.stmt.expression.Function;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzt on 12/30/16.
 * <p>
 * <h3></h3>
 */
public class UdfFilter {

  /**
   * used to implement:
   * <li>differences between select list generation and where condition generation: aggregate function</li>
   * <li>specific function requirement: non-equal join; no sub-query</li>
   */
  private final Map<Function, Possibility> preference = new HashMap<>();

  public UdfFilter(UdfFilter udfFilter) {
    preference.putAll(udfFilter.preference);
  }

  public UdfFilter() {
  }

  public UdfFilter addPreference(Function f, Possibility p) {
    preference.put(f, p);
    return this;
  }

  public UdfFilter addPreference(Function[] f, Possibility p) {
    for (Function function : f) {
      addPreference(function, p);
    }
    return this;
  }

  UdfFilter removePreference(Function f) {
    preference.remove(f);
    return this;
  }

  public boolean want(Function function) {
    Possibility possibility = preference.get(function);
    if (possibility == null) {
      possibility = Possibility.NORMAL;
    }
    return possibility.chooseFirstOrRandom(true, false);
  }
}
