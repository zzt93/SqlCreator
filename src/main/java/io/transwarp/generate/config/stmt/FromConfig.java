package io.transwarp.generate.config.stmt;

import io.transwarp.generate.config.op.JoinConfig;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by zzt on 1/17/17.
 * <p>
 * <h3></h3>
 */
public class FromConfig {

  private JoinConfig join;

  @XmlElement
  public JoinConfig getJoin() {
    return join;
  }

  public void setJoin(JoinConfig join) {
    this.join = join;
  }

  public boolean noJion() {
    return join == null;
  }
}
