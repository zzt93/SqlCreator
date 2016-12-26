package io.transwarp.generate.config;

import io.transwarp.db_specific.base.Dialect;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
public class Config {

  private static int randomMaxBitLen = 64;
  private static int randomListMaxLen = 10;
  private static int randomStrMaxLen = 100;
  private static int udfDepth = 10;
  private static int subQueryDepth = 0;
  private static int exprNumInSelect = 1;
  private static InputRelation inputRelation = InputRelation.RANDOM;
  private static Dialect cmp = Dialect.ORACLE;
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

  public static int getExprNumInSelect() {
    return exprNumInSelect;
  }

  public static class Builder {

    public Builder setRandomMaxBitLen(int randomMaxBitLen) {
      checkArgument(randomMaxBitLen < 64 && randomMaxBitLen > 0);
      Config.randomMaxBitLen = randomMaxBitLen;
      return this;
    }

    public Builder setRandomListMaxLen(int randomListMaxLen) {
      checkArgument(randomListMaxLen > 0);
      Config.randomListMaxLen = randomListMaxLen;
      return this;
    }

    public Builder setRandomStrMaxLen(int randomStrMaxLen) {
      checkArgument(randomStrMaxLen > 0);
      Config.randomStrMaxLen = randomStrMaxLen;
      return this;
    }

    public Builder setUdfDepth(int udfDepth) {
      checkArgument(udfDepth >= 0 && udfDepth < 10);
      Config.udfDepth = udfDepth;
      return this;
    }

    public Builder setSubQueryDepth(int subQueryDepth) {
      checkArgument(subQueryDepth >= 0 && subQueryDepth < 10);
      Config.subQueryDepth = subQueryDepth;
      return this;
    }

    public Builder setInputRelation(InputRelation inputRelation) {
      checkNotNull(inputRelation);
      Config.inputRelation = inputRelation;
      return this;
    }

    public Builder setDialect(Dialect dialect) {
      checkNotNull(dialect);
      Config.cmp = dialect;
      return this;
    }

    public Builder setBase(Dialect base) {
      checkNotNull(base);
      Config.base = base;
      return this;
    }

    public void build() {
      if (cmp == base) {
        throw new IllegalArgumentException();
      }
    }
  }
}
