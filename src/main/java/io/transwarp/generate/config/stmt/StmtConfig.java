package io.transwarp.generate.config.stmt;


import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.parse.sql.DDLParser;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.EnumMap;
import java.util.List;

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

  private List<Table> candidates;

  List<Table> getCandidates() {
    if (candidates != null) {
      return candidates;
    }
    return candidates = DDLParser.getTable(table, dialect);
  }

  @Override
  public StmtConfig setCandidates(List<Table> candidates) {
    this.candidates = candidates;
    return this;
  }

  public StmtConfig setFrom(List<Table> from) {
    // only need candidates, from is generated
    throw new NotImplementedException();
  }

  @Override
  public boolean lackChildConfig() {
    return false;
  }

  @Override
  public StmtConfig addDefaultConfig(List<Table> candidates, List<Table> from) {
    return this;
  }

  public abstract EnumMap<Dialect, String> generate(Dialect[] dialects);
}
