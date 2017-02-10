package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.ExprConfig;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class FilterOperatorConfig implements DefaultConfig<FilterOperatorConfig> {

  private ExprConfig operand;
  private List<Table> src, candidates;

  @XmlElement
  public ExprConfig getOperand() {
    return operand;
  }

  public FilterOperatorConfig setOperand(ExprConfig operand) {
    this.operand = operand;
    return this;
  }

  public FilterOperatorConfig setFrom(List<Table> from) {
    if (operand != null) {
      operand.setFrom(from);
    }
    this.src = from;
    return this;
  }

  @Override
  public FilterOperatorConfig setCandidates(List<Table> candidates) {
    if (operand != null) {
      operand.setCandidates(candidates);
    }
    this.candidates = candidates;
    return this;
  }

  @Override
  public boolean lackChildConfig() {
    return operand == null || operand.lackChildConfig();
  }

  @Override
  public FilterOperatorConfig addDefaultConfig() {
    if (operand == null) {
      operand = new ExprConfig(src, candidates);
    } else {
      operand.addDefaultConfig();
    }
    return this;
  }
}
