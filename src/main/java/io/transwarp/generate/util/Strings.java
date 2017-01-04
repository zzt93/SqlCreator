package io.transwarp.generate.util;

import java.util.Arrays;

/**
 * Created by zzt on 12/30/16.
 * <p>
 * <h3></h3>
 */
public class Strings {

  public static String[] of(String ...strings) {
    return strings;
  }

  public static String[] of(String s, int n) {
    String[] strings = new String[n];
    Arrays.fill(strings, s);
    return strings;
  }
}
