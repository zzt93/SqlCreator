package io.transwarp.generate.config;

import io.transwarp.generate.stmt.expression.CmpOp;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zzt on 1/3/17.
 * <p>
 * <h3>This class should be immutable</h3>
 */
public class PerGenerationConfig {

  // operand
  private int udfDepth;
  private int queryDepth;
  private InputRelation inputRelation;
  private final UdfFilter udfFilter = new UdfFilter();
  // from
  private int joinTimes;
  // select
  private int selectColMax;
  private int exprNumInSelect;

  private PerGenerationConfig(int udfDepth, int queryDepth, int joinTimes, int selectColMax, int exprNumInSelect, InputRelation inputRelation) {
    this.udfDepth = udfDepth;
    this.queryDepth = queryDepth;
    this.joinTimes = joinTimes;
    this.selectColMax = selectColMax;
    this.exprNumInSelect = exprNumInSelect;
    this.inputRelation = inputRelation;
  }

  public int getUdfDepth() {
    return udfDepth;
  }

  public int getQueryDepth() {
    return queryDepth;
  }

  public int getJoinTimes() {
    return joinTimes;
  }

  public int getSelectColMax() {
    return selectColMax;
  }

  public int getExprNumInSelect() {
    return exprNumInSelect;
  }

  public InputRelation getInputRelation() {
    return inputRelation;
  }

  public UdfFilter getUdfFilter() {
    return udfFilter;
  }

  public PerGenerationConfig decrementUdfDepth() {
    return new Builder().setUdfDepth(udfDepth - 1).create();
  }

  public PerGenerationConfig decrementQueryDepth() {
    final PerGenerationConfig config = new Builder().setQueryDepth(queryDepth - 1).create();

    if (!config.hasSubQuery()) {
      config.getUdfFilter()
          .addPreference(CmpOp.IN, Possibility.IMPOSSIBLE)
          .addPreference(CmpOp.NOT_IN, Possibility.IMPOSSIBLE)
          .addPreference(CmpOp.EXISTS, Possibility.IMPOSSIBLE);
    }
    return config;
  }

  public boolean hasSubQuery() {
    return queryDepth > 0;
  }

  public static class Builder {
    private static final int MAX_COLS = 20;

    private int udfDepth = FunctionDepth.SMALL;
    private int queryDepth = 1;
    private InputRelation inputRelation = InputRelation.SAME;
    private int joinTimes = 0;
    private int selectColMax = Builder.MAX_COLS;
    private int exprNumInSelect = 1;

    public Builder setUdfDepth(int udfDepth) {
      checkArgument(udfDepth >= 0 && udfDepth < 10);
      this.udfDepth = udfDepth;
      return this;
    }

    public Builder setQueryDepth(int queryDepth) {
      checkArgument(queryDepth >= 0 && queryDepth < 10);
      this.queryDepth = queryDepth;
      return this;
    }

    public Builder setJoinTimes(int joinTimes) {
      this.joinTimes = joinTimes;
      return this;
    }

    public Builder setSelectColMax(int selectColMax) {
      checkArgument(selectColMax <= MAX_COLS);
      this.selectColMax = selectColMax;
      return this;
    }

    public Builder setExprNumInSelect(int exprNumInSelect) {
      this.exprNumInSelect = exprNumInSelect;
      return this;
    }

    public Builder setInputRelation(InputRelation inputRelation) {
      checkNotNull(inputRelation);
      this.inputRelation = inputRelation;
      return this;
    }

    public PerGenerationConfig create() {
      checkArgument(exprNumInSelect <= selectColMax, "exprNumInSelect > selectColMax");
      return new PerGenerationConfig(udfDepth, queryDepth, joinTimes, selectColMax, exprNumInSelect, inputRelation);
    }
  }
}
