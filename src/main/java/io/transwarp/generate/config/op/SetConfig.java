package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.stmt.select.SelectResult;

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
  private String alias = TableUtil.INVALID_ALIAS;

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


  @XmlAttribute
  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  @Override
  public boolean lackChildConfig() {
    return candidates == null
        || (getSubQuery() == null || getSubQuery().lackChildConfig());
  }

  @Override
  public SetConfig addDefaultConfig(List<Table> candidates, List<Table> from) {
    setCandidates(candidates);

    if (getSubQuery() != null) {
      getSubQuery().addDefaultConfig(candidates, from);
      return this;
    }

    defaultChoice();
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

  Table toTable() {
    final QueryConfig subQuery = getSubQuery();
    assert subQuery != null;
    subQuery.getSelect().setUseStar(BiChoicePossibility.IMPOSSIBLE);
    return SelectResult.generateQuery(subQuery).toTable(getAlias());
  }

  void defaultChoice() {
    setSubQuery(QueryConfig.fromQuery(getCandidatesTables()));
  }
}
