package io.transwarp.generate.config;

import io.transwarp.db_specific.base.Dialect;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
public class Config {

  public static int randomMaxBitLen = 64;
  private static int randomListMaxLen = 10;
  public static int randomStrMaxLen = 100;
  private static int udfDepth = FunctionDepth.WITH_OPERATOR;
  private static int subQueryDepth = 0;
  private static InputRelation inputRelation = InputRelation.RANDOM;
  private static Dialect dialect = Dialect.ORACLE;
  private static Dialect base = Dialect.INCEPTOR;

  public static int getUdfDepth() {
    return udfDepth;
  }

  public static int getSubQueryDepth() {
    return subQueryDepth;
  }

  public static InputRelation getInputRelation() {
    return inputRelation;
  }

  public static Dialect getDialect() {
    return dialect;
  }

  public static Dialect getBase() {
    return base;
  }

  public static int getRandomListMaxLen() {
    return randomListMaxLen;
  }

  public Config setRandomListMaxLen(int randomListMaxLen) {
    Config.randomListMaxLen = randomListMaxLen;
    return this;
  }

  public static int getRandomStrMaxLen() {
    return randomStrMaxLen;
  }

  public Config setRandomStrMaxLen(int randomStrMaxLen) {
    Config.randomStrMaxLen = randomStrMaxLen;
    return this;
  }

  public Config setUdfDepth(int udfDepth) {
    Config.udfDepth = udfDepth;
    return this;
  }

  public Config setSubQueryDepth(int subQueryDepth) {
    Config.subQueryDepth = subQueryDepth;
    return this;
  }

  public Config setInputRelation(InputRelation inputRelation) {
    Config.inputRelation = inputRelation;
    return this;
  }

  public Config setDialect(Dialect dialect) {
    Config.dialect = dialect;
    return this;
  }

  public Config setBase(Dialect base) {
    Config.base = base;
    return this;
  }
}
