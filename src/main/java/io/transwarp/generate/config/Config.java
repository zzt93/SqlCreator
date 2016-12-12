package io.transwarp.generate.config;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
public class Config {


  private static int udfDepth = 0;
  private static int subQueryDepth = 0;

  public static int getUdfDepth() {
    return udfDepth;
  }

  public static void setUdfDepth(int udfDepth) {
    Config.udfDepth = udfDepth;
  }

  public static int getSubQueryDepth() {
    return subQueryDepth;
  }

  public static void setSubQueryDepth(int subQueryDepth) {
    Config.subQueryDepth = subQueryDepth;
  }

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
}
