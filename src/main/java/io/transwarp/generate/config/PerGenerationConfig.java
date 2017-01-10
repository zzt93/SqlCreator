package io.transwarp.generate.config;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.stmt.expression.AggregateOp;
import io.transwarp.generate.stmt.expression.CmpOp;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.parse.sql.DDLParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zzt on 1/3/17.
 * <p>
 * <h3>This class should be immutable</h3>
 */
public class PerGenerationConfig {

  // TODO may add input requirement: const, expr, sub-query -- by udf depth and Column#possiblity

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
  private Map<GenerationDataType, Possibility> results;


  private PerGenerationConfig(int udfDepth, int queryDepth, int joinTimes,
                              int selectColMax, int exprNumInSelect,
                              InputRelation inputRelation, UdfFilter udfFilter,
                              Table[] src,
                              Map<GenerationDataType, Possibility> results) {
    this.udfDepth = udfDepth;
    this.queryDepth = queryDepth;
    this.joinTimes = joinTimes;
    this.selectColMax = selectColMax;
    this.exprNumInSelect = exprNumInSelect;
    this.inputRelation = inputRelation;
    this.udfFilter = udfFilter;
    this.results = results;
    this.src = src;
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

  public Map<GenerationDataType, Possibility> getResults() {
    return results;
  }

  public boolean hasResultLimit() {
    return results.size() > 0;
  }

  public PerGenerationConfig selectListConfig() {
    final PerGenerationConfig config = new Builder(this).addImpossible(DataType.BOOL).create();
    config.getUdfFilter().addPreference(CmpOp.values(), Possibility.IMPOSSIBLE);
    return config;
  }

  public PerGenerationConfig whereConfig() {
    final PerGenerationConfig config = new Builder(this).addMust(DataType.BOOL).create();
    config.getUdfFilter().addPreference(AggregateOp.values(), Possibility.IMPOSSIBLE);
    return config;
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
    private HashMap<GenerationDataType, Possibility> results = new HashMap<>();
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
      results = new HashMap<>(config.results);
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
      checkArgument(selectColMax <= MAX_COLS && selectColMax > 0);
      this.selectColMax = selectColMax;
      return this;
    }

    public Builder setExprNumInSelect(int exprNumInSelect) {
      checkArgument(exprNumInSelect <= MAX_COLS && exprNumInSelect > 0);
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

    public Builder addMust(GenerationDataType... types) {
      for (GenerationDataType type : types) {
        results.put(type, Possibility.CERTAIN);
      }
      return this;
    }

    public Builder addImpossible(GenerationDataType... types) {
      for (GenerationDataType type : types) {
        results.put(type, Possibility.IMPOSSIBLE);
      }
      return this;
    }

    public Builder setSrc(Table... src) {
      this.src = src;
      return this;
    }

    public PerGenerationConfig create() {
      checkArgument(exprNumInSelect <= selectColMax, "exprNumInSelect > selectColMax");
      checkArgument(results.size() <= selectColMax, "results type count > selectColMax");
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
