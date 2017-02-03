package io.transwarp.generate.config.op;

import io.transwarp.generate.config.DefaultConfig;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by zzt on 1/20/17.
 * <p>
 * <h3></h3>
 */
@XmlType(name = "relationOperand")
public class RelationOperandConfig extends SetOperandConfig implements DefaultConfig<RelationOperandConfig> {
  private String table;

  @XmlElement
  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  @Override
  public boolean noConfig() {
    return table == null && getSubQuery() == null;
  }

  @Override
  public RelationOperandConfig setThisToDefaultConfig() {
    return this;
  }
}
