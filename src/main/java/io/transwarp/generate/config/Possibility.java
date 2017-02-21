package io.transwarp.generate.config;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by zzt on 2/21/17.
 * <p>
 * <h3></h3>
 */
public class Possibility {

  protected static ThreadLocalRandom random = ThreadLocalRandom.current();

  Possibility(double[] v) {
    double sum = 0;
    for (double v1 : v) {
      checkArgument(v1 >= 0);
      sum += v1;
    }
    checkArgument(sum <= 1);
    this.possibility = Arrays.copyOf(v, v.length);
  }

  private final double[] possibility;

  public double[] getPossibility() {
    return possibility;
  }

  public static Possibility evenPossibility(int n) {
    double[] possibility = new double[n - 1];
    Arrays.fill(possibility, 1.0 / n);
    return new Possibility(possibility);
  }

  /**
   * @param p n-1 possibility, nth possibility will be calculated by this obj
   * @return possibility for n choice
   */
  public static Possibility possibility(double... p) {
    return new Possibility(p);
  }

  public <T> T random(T... choice) {
    checkArgument(getPossibility().length + 1 == choice.length, "Wrong number of choice: " + Arrays.toString(choice));
    double v = random.nextDouble();
    int i;
    for (i = 0; i < getPossibility().length; i++) {
      if (v < getPossibility()[i]) {
        return choice[i];
      }
      v -= getPossibility()[i];
    }
    return choice[i];
  }
}
