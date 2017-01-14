package io.transwarp.generate.config.stmt_config;


import io.transwarp.db_specific.base.Dialect;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by zzt on 1/13/17.
 * <p>
 * <h3></h3>
 */
public class StmtConfig {

  private StmtConfig ref;
  private int num;
  private String table;
  private Dialect dialect;

  public StmtConfig getRef() {
    return ref;
  }

  public StmtConfig setRef(StmtConfig ref) {
    this.ref = ref;
    return this;
  }

  public int getNum() {
    return num;
  }

  public StmtConfig setNum(int num) {
    this.num = num;
    return this;
  }

  public String getTable() {
    return table;
  }

  public StmtConfig setTable(String table) {
    this.table = table;
    return this;
  }

  @XmlAttribute(name = "dialect")
  public Dialect getDialect() {
    return dialect;
  }

  public StmtConfig setDialect(Dialect dialect) {
    this.dialect = dialect;
    return this;
  }
}
