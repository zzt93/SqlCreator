package io.transwarp.generate.config.stmt;


import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
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

  private List<Table> candidates;

  List<Table> getCandidates() {
    return candidates;
  }

  @Override
  public StmtConfig setFromCandidates(List<Table> fromCandidates) {
    this.candidates = fromCandidates;
    return this;
  }

  public StmtConfig setStmtUse(List<Table> ignored) {
    // only need candidates, used table is generated
    throw new NotImplementedException();
  }

  @Override
  public boolean lackChildConfig() {
    return candidates == null;
  }

  @Override
  public StmtConfig addDefaultConfig(List<Table> fromCandidates, List<Table> ignored) {
    setFromCandidates(fromCandidates);
    return this;
  }

  public abstract EnumMap<Dialect, String> generate(Dialect[] dialects);

  @Override
  public StmtConfig deepCopyTo(StmtConfig stmtConfig) {
    stmtConfig.setNum(num);
    stmtConfig.setId(id + "-clone");
    return stmtConfig;
  }
}
