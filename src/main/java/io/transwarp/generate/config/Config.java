package io.transwarp.generate.config;

import io.transwarp.db_specific.base.Dialect;

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
  private static InputRelation inputRelation = InputRelation.RANDOM;
  private static Dialect dialect = Dialect.ORACLE;

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

  public static InputRelation getInputRelation() {
    return inputRelation;
  }

  public static void setInputRelation(InputRelation inputRelation) {
    Config.inputRelation = inputRelation;
  }

  public static Dialect getDialect() {
    return dialect;
  }

  public static void setDialect(Dialect dialect) {
    Config.dialect = dialect;
  }
}
