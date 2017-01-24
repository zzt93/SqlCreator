package io.transwarp.generate.stmt.expression;

import com.google.common.base.Preconditions;
import io.transwarp.generate.config.expr.UdfFilter;

import java.util.ArrayList;

/**
 * Created by zzt on 1/3/17.
 * <p>
 * <h3></h3>
 */
public class Functions {

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
    Preconditions.checkArgument(!functions.isEmpty());
    if (shouldNotFilter()) {
      return functions.get(0);
    }
    return udfFilter.getMostPossible(functions);
  }
}
