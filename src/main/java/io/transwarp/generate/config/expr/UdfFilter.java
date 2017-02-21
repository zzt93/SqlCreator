package io.transwarp.generate.config.expr;

import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.stmt.expression.AggregateOp;
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
  private final Map<Function, BiChoicePossibility> preference = new HashMap<>();
  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  public UdfFilter(UdfFilter udfFilter) {
    preference.putAll(udfFilter.preference);
  }

  public UdfFilter(Map<Function, BiChoicePossibility> preference) {
    this.preference.putAll(preference);
  }

  UdfFilter() {
  }

  private UdfFilter addPreference(Function f, BiChoicePossibility p) {
    preference.put(f, p);
    return this;
  }

  UdfFilter addPreference(Function[] f, BiChoicePossibility p) {
    for (Function function : f) {
      addPreference(function, p);
    }
    return this;
  }

  private BiChoicePossibility possibility(Function function) {
    BiChoicePossibility biChoicePossibility = preference.get(function);
    if (biChoicePossibility == null) {
      biChoicePossibility = BiChoicePossibility.NORMAL;
    }
    return biChoicePossibility;
  }


  public Function getMostPossible(ArrayList<Function> functions) {
    List<Function> fs = new ArrayList<>(functions.size());
    BiChoicePossibility max = BiChoicePossibility.IMPOSSIBLE;
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
    final Function function = fs.get(random.nextInt(fs.size()));
    decreasePossibility(function);
    return function;
  }

  private void decreasePossibility(Function function) {
    BiChoicePossibility biChoicePossibility = preference.get(function);
    if (biChoicePossibility == null) {
      biChoicePossibility = BiChoicePossibility.NORMAL;
    }

    if (function instanceof AggregateOp) {
      // make all other aggregate op impossible
      for (AggregateOp op : AggregateOp.values()) {
        preference.put(op, BiChoicePossibility.IMPOSSIBLE);
      }
    } else {
      preference.put(function, biChoicePossibility.decrease(0.05));
    }
  }

  public int size() {
    return preference.size();
  }

  boolean prefer(BiChoicePossibility biChoicePossibility, Function... values) {
    for (Function value : values) {
      if (possibility(value).compareTo(biChoicePossibility) > 0) {
        return true;
      }
    }
    return false;
  }
}
