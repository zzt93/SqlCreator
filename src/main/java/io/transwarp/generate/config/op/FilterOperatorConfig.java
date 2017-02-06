package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.ExprConfig;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class FilterOperatorConfig implements DefaultConfig<FilterOperatorConfig> {

  private ExprConfig operand;
  private Table[] src;

  @XmlElement
  public ExprConfig getOperand() {
    return operand;
  }

  public FilterOperatorConfig setOperand(ExprConfig operand) {
    this.operand = operand;
    return this;
  }

  public FilterOperatorConfig setSrc(Table[] src) {
    this.src = src;
    return this;
  }

  @Override
  public boolean lackChildConfig() {
    return operand == null;
  }

  @Override
  public FilterOperatorConfig addDefaultConfig() {
    operand = new ExprConfig(src);
    return this;
  }
}
