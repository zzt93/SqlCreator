package io.transwarp.generate.config.op;

import io.transwarp.generate.config.stmt.StmtConfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
@XmlType(name = "setOperand")
public class SetOperandConfig {
  private StmtConfig subQuery;

  private String desc;

  @XmlIDREF
  @XmlElement
  public StmtConfig getSubQuery() {
    return subQuery;
  }

  public void setSubQuery(StmtConfig subQuery) {
    this.subQuery = subQuery;
  }

  @XmlAttribute
  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
