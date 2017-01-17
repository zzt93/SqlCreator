package io.transwarp.generate.config.stmt;

import io.transwarp.generate.config.op.SetOperatorConfig;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by zzt on 1/17/17.
 * <p>
 * <h3></h3>
 */
public class FromConfig {

  private SetOperatorConfig join;

  @XmlElement
  public SetOperatorConfig getJoin() {
    return join;
  }

  public void setJoin(SetOperatorConfig join) {
    this.join = join;
  }
}
