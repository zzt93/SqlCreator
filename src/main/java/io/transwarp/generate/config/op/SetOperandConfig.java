package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.stmt.QueryConfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
@XmlType(name = "setOperand")
public class SetOperandConfig implements DefaultConfig<SetOperandConfig> {
  private QueryConfig subQuery;

  private String desc;

  private List<Table> src, candidates;

  @XmlIDREF
  @XmlElement
  public QueryConfig getSubQuery() {
    return subQuery;
  }

  public void setSubQuery(QueryConfig subQuery) {
    this.subQuery = subQuery;
  }

  @XmlAttribute
  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }


  @Override
  public boolean lackChildConfig() {
    return false;
  }

  @Override
  public SetOperandConfig addDefaultConfig() {
    return null;
  }

  public SetOperandConfig setFrom(List<Table> tables) {
    src = tables;
    return this;
  }

  @Override
  public SetOperandConfig setCandidates(List<Table> candidates) {
    if (subQuery != null) {
      subQuery.setCandidates(candidates);
    }
    this.candidates = candidates;
    return this;
  }

  public List<Table> getFromTables() {
    return src;
  }

  List<Table> getCandidatesTables() {
    return candidates;
  }
}
