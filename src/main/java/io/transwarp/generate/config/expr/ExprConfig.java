package io.transwarp.generate.config.expr;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.expr.adapter.PossibilityAdapter;
import io.transwarp.generate.config.expr.adapter.UdfFilterAdapter;
import io.transwarp.generate.config.stmt.QueryConfig;
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

  private UdfFilter udfFilter = new UdfFilter();

  private int udfDepth = INVALID;
  private String desc;
  private Possibility constOrColumnPossibility = Possibility.HALF;
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
      @XmlElement(name = "const", type = ExprConfig.class),
      @XmlElement(name = "expr", type = ExprConfig.class),
      @XmlElement(name = "column", type = ExprConfig.class)
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
  @XmlJavaTypeAdapter(PossibilityAdapter.class)
  public Possibility getConstOrColumnPossibility() {
    return constOrColumnPossibility;
  }

  public void setConstOrColumnPossibility(Possibility constOrColumnPossibility) {
    this.constOrColumnPossibility = constOrColumnPossibility;
  }

  @XmlAttribute
  public InputRelation getInputRelation() {
    return inputRelation;
  }

  public void setInputRelation(InputRelation inputRelation) {
    this.inputRelation = inputRelation;
  }

  public boolean hasNestedConfig() {
    return !operands.isEmpty();
  }

  @Override
  public boolean lackChildConfig() {
    return src == null || udfDepth == INVALID
        || recursiveConfig();
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
    }
    return candidateQuery;
  }

  @Override
  public ExprConfig addDefaultConfig(List<Table> candidates, List<Table> from) {
    setCandidates(candidates);
    setFrom(from);

    assert src != null;
    if (hasNestedConfig()) {
      udfDepth = HAS_NESTED_UDF_DEPTH;
      for (ExprConfig operand : operands) {
        operand.addDefaultConfig(candidates, from);
      }
    } else {
      udfDepth = NO_NESTED_UDF_DEPTH;
    }
    if (candidateQuery != null) {
      candidateQuery.addDefaultConfig(candidates, from);
    }
    return this;
  }

  public ExprConfig setFrom(List<Table> tables) {
    src = tables;
    return this;
  }

  @Override
  public ExprConfig setCandidates(List<Table> candidates) {
    this.candidates = candidates;
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
}
