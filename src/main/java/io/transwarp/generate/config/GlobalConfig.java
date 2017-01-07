package io.transwarp.generate.config;

import io.transwarp.db_specific.base.Dialect;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
public class GlobalConfig {

  public static final int VAR_ARGS_MAX_LEN = 5;
  private static final int randomMaxBitLen = 64;
  private static final int randomListMaxLen = 10;
  private static final int randomStrMaxLen = 100;

  private static Dialect cmp = Dialect.INCEPTOR;
  private static Dialect base = Dialect.ORACLE;

  public static Dialect getCmp() {
    return cmp;
  }

  public static Dialect getBase() {
    return base;
  }

  public static int getRandomListMaxLen() {
    return randomListMaxLen;
  }

  public static int getRandomMaxBitLen() {
    return randomMaxBitLen;
  }

  public static int getRandomStrMaxLen() {
    return randomStrMaxLen;
  }

  public static Dialect[] getCmpBase() {
    return new Dialect[]{getCmp(), getBase()};
  }

}
