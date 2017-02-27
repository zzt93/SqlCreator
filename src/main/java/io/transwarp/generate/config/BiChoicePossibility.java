package io.transwarp.generate.config;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class BiChoicePossibility extends Possibility implements Comparable<BiChoicePossibility> {

  public static final BiChoicePossibility CERTAIN = new BiChoicePossibility(1);
  public static final BiChoicePossibility NORMAL = new BiChoicePossibility(0.75);
  public static final BiChoicePossibility HALF = new BiChoicePossibility(0.5);
  public static final BiChoicePossibility IMPOSSIBLE = new BiChoicePossibility(0);


  public static final BiChoicePossibility LIST_OR_QUERY = new BiChoicePossibility(0.5);
  public static final BiChoicePossibility SELECT_COL_POSSIBILITY = new BiChoicePossibility(0.5);
  private static final double SMALLEST = 1.0 / (1 << 10);


  private BiChoicePossibility(double... v) {
    super(v);
    assert v.length == 1;
  }

  @SafeVarargs
  @Override
  public final <T> T random(T... choice) {
    checkArgument(2 == choice.length, "Wrong number of choice: " + Arrays.toString(choice));
    return random.nextDouble() < getPossibility()[0] ? choice[0] : choice[1];
  }

  @Override
  public int compareTo(BiChoicePossibility o) {
    checkArgument(getPossibility().length == o.getPossibility().length
        && getPossibility().length == 1, "Only implemented comparison for bi-choice possibility");
    return Double.compare(getPossibility()[0], o.getPossibility()[0]);
  }

  public BiChoicePossibility decrease(double minus) {
    final BiChoicePossibility res = new BiChoicePossibility(getPossibility());
    assert res.getPossibility()[0] > 0;
    if (res.getPossibility()[0] < minus) {
      res.getPossibility()[0] = SMALLEST;
    } else {
      res.getPossibility()[0] -= minus;
    }
    return res;
  }

  /**
   * @param p n-1 possibility, nth possibility will be calculated by this obj
   * @return possibility for n choice
   */
  public static BiChoicePossibility possibility(double... p) {
    assert p.length == 1;
    return new BiChoicePossibility(p);
  }

}
