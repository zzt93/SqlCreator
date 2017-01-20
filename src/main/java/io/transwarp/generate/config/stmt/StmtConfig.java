package io.transwarp.generate.config.stmt;


import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
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
public abstract class StmtConfig {

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

  public Table[] getSrc() {
    return DDLParser.getTable(table, dialect);
  }
}
