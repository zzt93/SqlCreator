package io.transwarp.generate.config.expr;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.adapter.UdfFilterAdapter;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.type.GenerationDataType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 * The default value is from xsd file
 */
public class ExprConfig implements DefaultConfig<ExprConfig> {

  private List<ExprConfig> operands;

  private UdfFilter udfFilter = new UdfFilter();

  private int udfDepth = 3;
  private String desc;
  private double constOrColumnPossibility = 0.5;
  private InputRelation inputRelation = InputRelation.SAME;

  private QueryConfig subQuery;

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

  @XmlAttribute
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
    return operands != null && !operands.isEmpty();
  }

  @Override
  public boolean lackChildConfig() {
    return src == null;
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
    return this;
  }

  public ExprConfig setFrom(List<Table> tables) {
    if (hasNestedConfig()) {
      for (ExprConfig operand : operands) {
        operand.setFrom(tables);
      }
    }
    src = tables;
    return this;
  }

  @Override
  public ExprConfig setCandidates(List<Table> candidates) {
    if (hasNestedConfig()) {
      for (ExprConfig operand : operands) {
        operand.setCandidates(candidates);
      }
    }
    if (subQuery != null) {
      subQuery.setCandidates(candidates);
    }
    this.candidates = candidates;
    return this;
  }

  public List<Table> getTables() {
    return src;
  }
}
