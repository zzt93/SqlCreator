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
@XmlType(name = "set")
public class SetConfig implements DefaultConfig<SetConfig> {
  private QueryConfig subQuery;

  private String desc;

  private List<Table> candidates;

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
    return candidates == null;
  }

  @Override
  public SetConfig addDefaultConfig(List<Table> candidates, List<Table> from) {
    setCandidates(candidates);

    if (subQuery != null) {
      subQuery.addDefaultConfig(candidates, from);
    }

    return this;
  }

  public SetConfig setFrom(List<Table> tables) {
    // this class only has candidates
    return this;
  }

  @Override
  public SetConfig setCandidates(List<Table> candidates) {
    this.candidates = candidates;
    return this;
  }

  List<Table> getCandidatesTables() {
    return candidates;
  }
}
