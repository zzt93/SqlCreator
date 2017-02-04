package io.transwarp.generate.config.stmt;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.op.JoinConfig;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by zzt on 1/17/17.
 * <p>
 * <h3></h3>
 */
public class FromConfig implements DefaultConfig<FromConfig> {

  private JoinConfig join;
  private Table[] src;

  @XmlElement
  public JoinConfig getJoin() {
    return join;
  }

  public void setJoin(JoinConfig join) {
    this.join = join;
  }

  public FromConfig setSrc(Table[] src) {
    this.src = src;
    join.setSrc(src);
    return this;
  }

  public Table[] getTables() {
    return src;
  }

  @Override
  public boolean lackConfig() {
    return join == null;
  }

  @Override
  public FromConfig addDefaultConfig(FromConfig t) {
    join = new JoinConfig();
    join.addDefaultConfig(null);
    return this;
  }
}
