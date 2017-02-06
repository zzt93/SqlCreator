package io.transwarp.generate.config.stmt;


import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.parse.sql.DDLParser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by zzt on 1/13/17.
 * <p>
 * <h3></h3>
 */
@XmlSeeAlso({QueryConfig.class, UpdateStmtConfig.class})
public abstract class StmtConfig implements DefaultConfig<StmtConfig> {

  private String id;

  private int num;
  private String table;
  private Dialect dialect;

  @XmlAttribute
  @XmlID
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @XmlAttribute
  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  @XmlAttribute
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

  private Table[] tables;

  public Table[] getSrc() {
    if (tables != null) {
      return tables;
    }
    return tables = DDLParser.getTable(table, dialect);
  }

  public StmtConfig setSrc(Table[] src) {
    tables = src;
    return this;
  }

  @Override
  public boolean lackChildConfig() {
    return false;
  }

  @Override
  public StmtConfig addDefaultConfig() {
    return this;
  }
}
