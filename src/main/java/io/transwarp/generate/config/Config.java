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

  private static int udfDepth = FunctionDepth.SINGLE;
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

}
