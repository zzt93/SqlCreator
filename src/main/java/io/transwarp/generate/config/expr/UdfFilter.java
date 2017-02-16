package io.transwarp.generate.config.expr;

import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.stmt.expression.Function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  public UdfFilter(UdfFilter udfFilter) {
    preference.putAll(udfFilter.preference);
  }

  public UdfFilter(Map<Function, Possibility> preference) {
    this.preference.putAll(preference);
  }

  public UdfFilter() {}

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

  private Possibility possibility(Function function) {
    Possibility possibility = preference.get(function);
    if (possibility == null) {
      possibility = Possibility.NORMAL;
    }
    return possibility;
  }


  public Function getMostPossible(ArrayList<Function> functions) {
    // TODO 2/16/17 fix nested aggregate op
    List<Function> fs = new ArrayList<>(functions.size());
    Possibility max = Possibility.IMPOSSIBLE;
    for (Function function : functions) {
      final int res = possibility(function).compareTo(max);
      if (res > 0) {
        fs.clear();
        fs.add(function);
        max = possibility(function);
      } else if (res == 0) {
        fs.add(function);
      }
    }
    return fs.get(random.nextInt(fs.size()));
  }

  public int size() {
    return preference.size();
  }
}
