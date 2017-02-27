package io.transwarp.generate.config;

/**
 * Created by zzt on 2/17/17.
 * <p>
 * <h3></h3>
 */
public class FixedParams {
  public static final int VAR_ARGS_MAX_LEN = 5;
  private static final int randomBitsMaxLen = 64;
  private static final int randomListMaxLen = 10;
  private static final int randomStrMaxLen = 100;

  public static int getRandomListMaxLen() {
    return randomListMaxLen;
  }

  public static int getRandomBitsMaxLen() {
    return randomBitsMaxLen;
  }

  public static int getRandomStrMaxLen() {
    return randomStrMaxLen;
  }
}
