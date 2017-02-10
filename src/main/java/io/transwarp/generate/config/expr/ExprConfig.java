package io.transwarp.generate.config.expr;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
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

  private static final int NO_NESTED_UDF_DEPTH = 1;
  private static final int HAS_NESTED_UDF_DEPTH = 1;
  private List<ExprConfig> operands = new ArrayList<>();

  private UdfFilter udfFilter = new UdfFilter();

  private int udfDepth = 3;
  private String desc;
  private double constOrColumnPossibility = 0.5;
  private InputRelation inputRelation = InputRelation.SAME;

  private QueryConfig subQuery;

  /*
  ---------------------- generate field -----------------
   */
  private List<Table> src;
  private List<Table> candidates;

  public ExprConfig() {
  }

  public ExprConfig(List<Table> src, List<Table> candidates) {
    this.src = src;
    this.candidates = candidates;
    addDefaultConfig();
  }

  @XmlAttribute
  @XmlIDREF
  public QueryConfig getSubQuery() {
    return subQuery;
  }

  public void setSubQuery(QueryConfig subQuery) {
    this.subQuery = subQuery;
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
  public double getConstOrColumnPossibility() {
    return constOrColumnPossibility;
  }

  public void setConstOrColumnPossibility(double constOrColumnPossibility) {
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
    return udfDepth == 0 || src == null || opsConfig();
  }

  private boolean opsConfig() {
    for (ExprConfig operand : operands) {
      if (operand.lackChildConfig()) {
        return true;
      }
    }
    return false;
  }

  public QueryConfig getSubQuery(GenerationDataType dataType) {
    assert candidates != null;
    if (subQuery == null) {
      subQuery = QueryConfig.defaultWhereExpr(candidates, dataType);
    }
    return subQuery;
  }

  @Override
  public ExprConfig addDefaultConfig() {
    assert src != null;
    if (!hasNestedConfig()) {
      udfDepth = NO_NESTED_UDF_DEPTH;
      return this;
    } else {
      udfDepth = HAS_NESTED_UDF_DEPTH;
    }
    for (ExprConfig operand : operands) {
      operand.setFrom(src);
    }
    return this;
  }

  public ExprConfig setFrom(List<Table> tables) {
    for (ExprConfig operand : operands) {
      operand.setFrom(tables);
    }
    src = tables;
    return this;
  }

  @Override
  public ExprConfig setCandidates(List<Table> candidates) {
    for (ExprConfig operand : operands) {
      operand.setCandidates(candidates);
    }
    if (subQuery != null) {
      subQuery.setCandidates(candidates);
    }
    this.candidates = candidates;
    return this;
  }

  public List<Table> getFrom() {
    return src;
  }

  public static ExprConfig defaultExpr(ExprConfig config) {
    return new ExprConfig(config.getFrom(), config.candidates);
  }
}
