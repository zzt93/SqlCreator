package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.config.expr.UdfFilter;

import java.util.ArrayList;

/**
 * Created by zzt on 1/3/17.
 * <p>
 * <h3></h3>
 */
public class Functions {

  public static final Functions EMPTY = new Functions(0);

  private ArrayList<Function> functions;

  Functions(int i) {
    functions = new ArrayList<>(i);
  }

  private Functions(ArrayList<Function> fs) {
    functions = fs;
  }

  int size() {
    return functions.size();
  }

  public boolean add(Function function) {
    return functions.add(function);
  }

  public Function get(int i) {
    return functions.get(i);
  }

  Functions filter(UdfFilter udfFilter) {
    ArrayList<Function> fs = new ArrayList<>(functions.size());
    for (Function function : functions) {
      if (udfFilter.want(function)) {
        fs.add(function);
      }
    }
    return new Functions(fs);
  }

  boolean isEmpty() {
    return functions.isEmpty();
  }

  boolean shouldNotFilter() {
    return size() == 1;
  }
}
