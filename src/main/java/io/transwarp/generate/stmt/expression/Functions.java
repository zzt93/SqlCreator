package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.config.UDFFilter;

import java.util.ArrayList;
import java.util.Iterator;

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

  int size() {
    return functions.size();
  }

  public boolean add(Function function) {
    return functions.add(function);
  }

  public Function get(int i) {
    return functions.get(i);
  }

  Functions filter(UDFFilter udfFilter) {
    for (Iterator<Function> it = functions.iterator(); it.hasNext(); ) {
      Function next = it.next();
      if (!udfFilter.want(next)) {
        it.remove();
      }
    }
    return this;
  }

  public boolean isEmpty() {
    return functions.isEmpty();
  }
}
