package io.transwarp.generate.config.expr;

import io.transwarp.db_specific.DialectSpecific;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.adapter.BiChoicePossibilityAdapter;
import io.transwarp.generate.config.expr.adapter.UdfFilterAdapter;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.stmt.expression.AggregateOp;
import io.transwarp.generate.stmt.expression.Function;
import io.transwarp.generate.type.GenerationDataType;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 * The default value is from xsd file
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ExprConfig implements DefaultConfig<ExprConfig> {

  private static final int NO_NESTED_UDF_DEPTH = 3;
  private static final int HAS_NESTED_UDF_DEPTH = 0;
  private static final int NESTED_EXPR_UDF_DEPTH = 1;
  private static final int INVALID = -1;

  private List<ExprConfig> operands = new ArrayList<>();
  private boolean useAggregateOp;

  private UdfFilter udfFilter = new UdfFilter();

  private int udfDepth = INVALID;
  private String desc;
  private BiChoicePossibility constOrColumnPossibility = BiChoicePossibility.HALF;
  private InputRelation inputRelation = InputRelation.SAME;

  private QueryConfig candidateQuery;

  /*
  ---------------------- generate field -----------------
   */
  private List<Table> src;
  private List<Table> candidates;

  public ExprConfig() {
  }

  public ExprConfig(List<Table> candidates, List<Table> src) {
    this.candidates = candidates;
    this.src = src;
    addDefaultConfig(candidates, src);
  }

  @XmlAttribute
  @XmlIDREF
  public QueryConfig getCandidateQuery() {
    return candidateQuery;
  }

  public void setCandidateQuery(QueryConfig candidateQuery) {
    this.candidateQuery = candidateQuery;
  }

  @XmlElements({
      @XmlElement(name = "constExpr", type = ExprConfig.class),
      @XmlElement(name = "expr", type = ExprConfig.class),
      @XmlElement(name = "columnExpr", type = ExprConfig.class)
  })
  public List<ExprConfig> getOperands() {
    return operands;
  }

  public ExprConfig setOperands(List<ExprConfig> operands) {
    this.operands = operands;
    return this;
  }

  @XmlElement(name = "udfConfig")
  @XmlJavaTypeAdapter(UdfFilterAdapter.class)
  public UdfFilter getUdfFilter() {
    return udfFilter;
  }

  public void setUdfFilter(UdfFilter udfFilter) {
    this.udfFilter = udfFilter;
  }

  @XmlElement
  public int getUdfDepth() {
    return udfDepth;
  }

  public void setUdfDepth(int udfDepth) {
    this.udfDepth = udfDepth;
  }

  @XmlAttribute
  public String getDesc() {
    return desc;
  }

  public ExprConfig setDesc(String desc) {
    this.desc = desc;
    return this;
  }

  @XmlAttribute
  @XmlJavaTypeAdapter(BiChoicePossibilityAdapter.class)
  public BiChoicePossibility getConstOrColumnPossibility() {
    return constOrColumnPossibility;
  }

  public void setConstOrColumnPossibility(BiChoicePossibility constOrColumnPossibility) {
    this.constOrColumnPossibility = constOrColumnPossibility;
  }

  @XmlAttribute
  public InputRelation getInputRelation() {
    return inputRelation;
  }

  public void setInputRelation(InputRelation inputRelation) {
    this.inputRelation = inputRelation;
  }

  @XmlAttribute(name = "useAggregateOp")
  public boolean isUseAggregateOp() {
    return useAggregateOp;
  }

  public void setUseAggregateOp(boolean useAggregateOp) {
    this.useAggregateOp = useAggregateOp;
  }

  public boolean hasNestedConfig() {
    return !operands.isEmpty();
  }

  private boolean recursiveConfig() {
    for (ExprConfig operand : operands) {
      if (operand.lackChildConfig()) {
        return true;
      }
    }
    return false;
  }

  public QueryConfig getSubQuery(GenerationDataType dataType) {
    assert candidates != null;
    if (candidateQuery == null) {
      candidateQuery = QueryConfig.defaultWhereExpr(candidates, dataType);
      return candidateQuery;
    }
    if (!candidateQuery.singleColumn()) {
      throw new IllegalArgumentException("SubQuery in where statement has more than one column: " + candidateQuery.getId());
    }
    if (candidateQuery.noResType()) {
      return candidateQuery.addResType(dataType);
    } else if (candidateQuery.getResType(0) != dataType) {
      return QueryConfig.defaultWhereExpr(candidates, dataType);
    }
    return candidateQuery;
  }

  private boolean aggregateOpHandled = false;

  @Override
  public boolean lackChildConfig() {
    return candidates == null || src == null
        || udfDepth == INVALID || !aggregateOpHandled
        || recursiveConfig();
  }

  @Override
  public ExprConfig addDefaultConfig(List<Table> fromCandidates, List<Table> fatherStmtUse) {
    setFromCandidates(fromCandidates);
    setStmtUse(fatherStmtUse);
    assert src != null;

    if (hasNestedConfig()) {
      udfDepth = HAS_NESTED_UDF_DEPTH;
      for (ExprConfig operand : operands) {
        operand.addDefaultConfig(fromCandidates, fatherStmtUse);
      }
    } else if (udfDepth == INVALID) {
      udfDepth = NO_NESTED_UDF_DEPTH;
    }
    if (candidateQuery != null) {
      candidateQuery.addDefaultConfig(fromCandidates, fatherStmtUse);
    }
    aggregateOpHandled = true;
    if (!useAggregateOp) {
      noAggregateOp();
    } else {
      if (preferAggregate()) {
        replaceDepthWithNestedExpr();
      }
    }
    return this;
  }

  private boolean preferAggregate() {
    return udfFilter.prefer(BiChoicePossibility.NORMAL, AggregateOp.values());
  }

  @DialectSpecific
  private void replaceDepthWithNestedExpr() {
    if (udfDepth >= 1) {
      // add nested config
      if (operands.isEmpty()) {
        operands.add(defaultNestedExpr(this));
      }
      udfDepth = HAS_NESTED_UDF_DEPTH;
    }
  }

  @Override
  public ExprConfig setStmtUse(List<Table> stmtUse) {
    src = stmtUse;
    return this;
  }

  @Override
  public ExprConfig setFromCandidates(List<Table> fromCandidates) {
    this.candidates = fromCandidates;
    return this;
  }

  public List<Table> getFrom() {
    return src;
  }

  public static ExprConfig defaultNestedExpr(ExprConfig config) {
    final ExprConfig exprConfig = new ExprConfig(config.candidates, config.getFrom());
    exprConfig.setUdfDepth(NESTED_EXPR_UDF_DEPTH);
    return exprConfig;
  }

  private void noAggregateOp() {
    udfFilter.addPreference(AggregateOp.values(), BiChoicePossibility.IMPOSSIBLE);
  }

  public UdfFilter addPreference(Function[] f, BiChoicePossibility p) {
    return udfFilter.addPreference(f, p);
  }
}
