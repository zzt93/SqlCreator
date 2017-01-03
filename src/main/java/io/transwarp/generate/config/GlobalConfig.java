package io.transwarp.generate.config;

import io.transwarp.db_specific.base.Dialect;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
public class GlobalConfig {

  private static int randomMaxBitLen = 64;
  private static int randomListMaxLen = 10;
  private static int randomStrMaxLen = 100;

  private static Dialect cmp = Dialect.INCEPTOR;
  private static Dialect base = Dialect.ORACLE;

  private static int udfDepth = FunctionDepth.SMALL;
  private static int queryDepth = 1;
  private static int joinTimes = 0;
  private static final int MAX_COLS = 20;
  private static int selectColMax = MAX_COLS;
  private static int exprNumInSelect = 1;
  private static InputRelation inputRelation = InputRelation.RANDOM;
  private static final UDFFilter UDF_CHOOSE_OPTION = new UDFFilter();

  public static UDFFilter getUdfChooseOption() {
    return UDF_CHOOSE_OPTION;
  }

  public static int getUdfDepth() {
    return udfDepth;
  }

  public static int getQueryDepth() {
    return queryDepth;
  }

  public static int getJoinTimes() {
    return joinTimes;
  }

  public static int getSelectColMax() {
    return selectColMax;
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

  public static Dialect[] getBaseCmp() {
    return new Dialect[]{getBase(), getCmp()};
  }


  public static class Builder {

    public Builder setSelectColMax(int selectColMax) {
      checkArgument(selectColMax <= MAX_COLS);
      GlobalConfig.selectColMax = selectColMax;
      return this;
    }

    public Builder setRandomMaxBitLen(int randomMaxBitLen) {
      checkArgument(randomMaxBitLen < 64 && randomMaxBitLen > 0);
      GlobalConfig.randomMaxBitLen = randomMaxBitLen;
      return this;
    }

    public Builder setRandomListMaxLen(int randomListMaxLen) {
      checkArgument(randomListMaxLen > 0);
      GlobalConfig.randomListMaxLen = randomListMaxLen;
      return this;
    }

    public Builder setRandomStrMaxLen(int randomStrMaxLen) {
      checkArgument(randomStrMaxLen > 0);
      GlobalConfig.randomStrMaxLen = randomStrMaxLen;
      return this;
    }

    public Builder setUdfDepth(int udfDepth) {
      checkArgument(udfDepth >= 0 && udfDepth < 10);
      GlobalConfig.udfDepth = udfDepth;
      return this;
    }

    public Builder setSubQueryDepth(int subQueryDepth) {
      checkArgument(subQueryDepth >= 0 && subQueryDepth < 10);
      GlobalConfig.queryDepth = subQueryDepth;
      return this;
    }

    public Builder setInputRelation(InputRelation inputRelation) {
      checkNotNull(inputRelation);
      GlobalConfig.inputRelation = inputRelation;
      return this;
    }

    public Builder setDialect(Dialect dialect) {
      checkNotNull(dialect);
      GlobalConfig.cmp = dialect;
      return this;
    }

    public Builder setBase(Dialect base) {
      checkNotNull(base);
      GlobalConfig.base = base;
      return this;
    }

    public void build() {
      if (cmp == base) {
        throw new IllegalArgumentException();
      }
      if (exprNumInSelect > selectColMax) {
        throw new IllegalArgumentException("exprNumInSelect > selectColMax");
      }
    }
  }
}
