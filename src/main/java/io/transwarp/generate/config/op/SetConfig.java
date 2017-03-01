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

  private String desc = "";

  private List<Table> candidates;
  private String alias = TableUtil.INVALID_ALIAS;

  @XmlIDREF
  @XmlElement
  public QueryConfig getSubQuery() {
    return subQuery;
  }

  public void setSubQuery(QueryConfig subQuery) {
    this.subQuery = QueryConfig.deepCopy(subQuery);
  }

  void noCopySetSubQuery(QueryConfig subQuery) {
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
        || (!hasQuery() || getSubQuery().lackChildConfig());
  }

  @Override
  public SetConfig addDefaultConfig(List<Table> fromCandidates, List<Table> fatherStmtUse) {
    setFromCandidates(fromCandidates);

    if (hasQuery()) {
      getSubQuery().addDefaultConfig(fromCandidates, fatherStmtUse);
      return this;
    }

    defaultChoice();
    return this;
  }

  private boolean hasQuery() {
    return getSubQuery() != null;
  }

  public SetConfig setStmtUse(List<Table> stmtUse) {
    // this class only has candidates
    return this;
  }

  @Override
  public SetConfig setFromCandidates(List<Table> fromCandidates) {
    this.candidates = fromCandidates;
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
    noCopySetSubQuery(QueryConfig.fromQuery(getCandidatesTables()));
  }

  @Override
  public SetConfig deepCopyTo(SetConfig t) {
    t.setDesc(desc);
    t.setAlias(alias);
    if (hasQuery()) {
      t.setSubQuery(subQuery.deepCopyTo(new QueryConfig()));
    }
    return t;
  }
}
