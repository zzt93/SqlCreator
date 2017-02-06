package io.transwarp.generate.config.expr;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.adapter.UdfFilterAdapter;
import io.transwarp.generate.config.stmt.QueryConfig;

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

  private Table[] src;

  public ExprConfig() {
  }

  public ExprConfig(Table[] src) {
    this.src = src;
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
    return false;
  }

  @Override
  public ExprConfig addDefaultConfig() {
    return this;
  }

  @Override
  public ExprConfig setSrc(Table[] tables) {
    src = tables;
    return this;
  }

  public Table[] getTables() {
    return src;
  }
}
