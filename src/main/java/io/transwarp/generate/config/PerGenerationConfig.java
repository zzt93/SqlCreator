package io.transwarp.generate.config;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.stmt.expression.CmpOp;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.parse.sql.DDLParser;

import java.util.Arrays;

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
  private UdfFilter udfFilter;
  // from
  private int joinTimes;
  private Table[] src;
  // select
  private int selectColMax;
  private int exprNumInSelect;
  private GenerationDataType[] results;


  private PerGenerationConfig(int udfDepth, int queryDepth, int joinTimes,
                              int selectColMax, int exprNumInSelect,
                              InputRelation inputRelation, UdfFilter udfFilter,
                              Table[] src,
                              GenerationDataType[] results) {
    this.udfDepth = udfDepth;
    this.queryDepth = queryDepth;
    this.joinTimes = joinTimes;
    this.selectColMax = selectColMax;
    this.exprNumInSelect = exprNumInSelect;
    this.inputRelation = inputRelation;
    this.udfFilter = udfFilter;
    this.results = results;
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
    return new Builder(this).setUdfDepth(udfDepth - 1).create();
  }

  public PerGenerationConfig decrementQueryDepth() {
    final PerGenerationConfig config = new Builder(this).setQueryDepth(queryDepth - 1).create();

    if (!config.hasSubQuery()) {
      config.getUdfFilter()
          .addPreference(CmpOp.IN_QUERY, Possibility.IMPOSSIBLE)
          .addPreference(CmpOp.NOT_IN_QUERY, Possibility.IMPOSSIBLE)
          .addPreference(CmpOp.EXISTS, Possibility.IMPOSSIBLE);
    }
    return config;
  }

  private boolean hasSubQuery() {
    return queryDepth > 0;
  }

  public Table[] getSrc() {
    return src;
  }

  public GenerationDataType[] getResults() {
    return results;
  }

  public boolean hasResultLimit() {
    return results.length > 0;
  }

  public static class Builder {
    private static final int MAX_COLS = 20;

    private int udfDepth = FunctionDepth.SMALL;
    private int queryDepth = 1;
    private InputRelation inputRelation = InputRelation.SAME;
    private int joinTimes = 0;
    private int selectColMax = Builder.MAX_COLS;
    private int exprNumInSelect = 1;
    private UdfFilter udfFilter = new UdfFilter();
    private GenerationDataType[] results = new GenerationDataType[0];
    private Table[] src = new Table[0];

    public Builder() {
    }

    public Builder(PerGenerationConfig config) {
      udfDepth = config.udfDepth;
      queryDepth = config.queryDepth;
      inputRelation = config.inputRelation;
      joinTimes = config.joinTimes;
      selectColMax = config.selectColMax;
      exprNumInSelect = config.exprNumInSelect;
      udfFilter = new UdfFilter(config.udfFilter);
      src = Arrays.copyOf(config.src, config.src.length);
      results = Arrays.copyOf(config.results, config.results.length);
    }

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

    public Builder setUdfFilter(UdfFilter udfFilter) {
      this.udfFilter = udfFilter;
      return this;
    }

    public Builder setResults(GenerationDataType... results) {
      this.results = results;
      return this;
    }

    public Builder setSrc(Table... src) {
      this.src = src;
      return this;
    }

    public PerGenerationConfig create() {
      checkArgument(exprNumInSelect <= selectColMax, "exprNumInSelect > selectColMax");
      checkArgument(results.length <= selectColMax, "results type count > selectColMax");
      if (src.length == 0) {
        src = DDLParser.getTable();
      }
      return new PerGenerationConfig(udfDepth, queryDepth, joinTimes,
          selectColMax, exprNumInSelect,
          inputRelation, udfFilter,
          src, results);
    }
  }
}
