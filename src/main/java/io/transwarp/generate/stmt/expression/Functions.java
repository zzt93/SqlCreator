package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.config.expr.UdfFilter;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 1/3/17.
 * <p>
 * <h3></h3>
 */
public class Functions {

  private static ThreadLocalRandom random = ThreadLocalRandom.current();
  private ArrayList<Function> functions;

  Functions(int i) {
    functions = new ArrayList<>(i);
  }

  public boolean add(Function function) {
    return functions.add(function);
  }

  public Function get(int i) {
    return functions.get(i);
  }

  private boolean shouldNotFilter() {
    return functions.size() == 1;
  }

  public Function random(UdfFilter udfFilter) {
    if (shouldNotFilter()) {
      return functions.get(0);
    }
    for (Function function : functions) {
      if (udfFilter.want(function)) {

      }
    }
    return functions.get(random.nextInt(functions.size()));
  }
}
