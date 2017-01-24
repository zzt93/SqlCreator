package io.transwarp.generate.config;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class Possibility implements Comparable<Possibility> {

  public static final Possibility CERTAIN = new Possibility(1);
  public static final Possibility PREFER = new Possibility(0.95);
  public static final Possibility NORMAL = new Possibility(0.75);
  public static final Possibility DENY = new Possibility(0.5);
  public static final Possibility IMPOSSIBLE = new Possibility(0);

  public static final Possibility HALF = new Possibility(0.5);

  public static final Possibility LIST_OR_QUERY = new Possibility(0.5);
  public static final Possibility SELECT_COL_POSSIBILITY = new Possibility(0.05);
  public static final Possibility COL_CONST_POSSIBILITY = new Possibility(0.7);

  private final double[] possibility;

  public static Possibility evenPossibility(int n) {
    double[] possibility = new double[n - 1];
    Arrays.fill(possibility, 1.0 / n);
    return new Possibility(Arrays.copyOf(possibility, possibility.length));
  }

  /**
   * @param p n-1 possibility, nth possibility will be calculated by this obj
   * @return possibility for n choice
   */
  public static Possibility possibility(double... p) {
    return new Possibility(p);
  }

  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  private Possibility(double... v) {
    double sum = 0;
    for (double v1 : v) {
      sum += v1;
    }
    checkArgument(sum <= 1);
    this.possibility = v;
  }

  @SafeVarargs
  public final <T> T chooseFirstOrRandom(T... choice) {
    checkArgument(possibility.length + 1 == choice.length, "Wrong number of choice: " + Arrays.toString(choice));
    return random.nextDouble() < possibility[0] ? choice[0] : choice[random.nextInt(choice.length - 1) + 1];
  }

  @SafeVarargs
  public final <T> T random(T... choice) {
    checkArgument(possibility.length + 1 == choice.length, "Wrong number of choice: " + Arrays.toString(choice));
    double v = random.nextDouble();
    int i;
    for (i = 0; i < possibility.length; i++) {
      if (v < possibility[i]) {
        return choice[i];
      }
      v -= possibility[i];
    }
    return choice[i];
  }

  @Override
  public int compareTo(Possibility o) {
    checkArgument(possibility.length == o.possibility.length
        && possibility.length == 1);
    return Double.compare(possibility[0], o.possibility[0]);
  }
}
