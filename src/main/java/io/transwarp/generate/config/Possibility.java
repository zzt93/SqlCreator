package io.transwarp.generate.config;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public enum Possibility {

  SELECT_POSSIBILITY(0.75),
  NAME_CONST_POSSIBILITY(0.95);

  private double[] possibility;

  private Possibility setPossibility(double[] possibility) {
    this.possibility = possibility;
    return this;
  }

  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  Possibility(double... v) {
    this.possibility = v;
  }

  @SafeVarargs
  public final <T> T chooseFirst(T... choice) {
    if (possibility.length + 1 != choice.length) {
      throw new IllegalArgumentException("Wrong number of choice: " + Arrays.toString(choice));
    }
    return random.nextDouble() < possibility[0] ? choice[0] : choice[1];
  }
}
