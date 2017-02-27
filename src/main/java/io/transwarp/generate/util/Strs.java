package io.transwarp.generate.util;

import java.util.Arrays;

/**
 * Created by zzt on 12/30/16.
 * <p>
 * <h3></h3>
 */
public class Strs {

  private static final String STRING_DELIMITER = "'";

  public static String[] of(String ...strings) {
    return strings;
  }

  public static String[] repeat(String s, int n) {
    String[] strings = new String[n];
    Arrays.fill(strings, s);
    return strings;
  }

  public static String sqlString(Object s) {
    return STRING_DELIMITER + s + STRING_DELIMITER;
  }
}
