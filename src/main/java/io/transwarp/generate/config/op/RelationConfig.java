package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.stmt.select.SelectResult;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by zzt on 1/20/17.
 * <p>
 * <h3></h3>
 */
@XmlType(name = "relation")
public class RelationConfig extends SetConfig {
  private static final String EMPTY = "";
  private String tableName = EMPTY;
  private JoinConfig joinConfig;

  /**
   * the mapped table of tableName
   *
   * @see #tableName
   */
  private Table operand;

  public RelationConfig() {
  }

  RelationConfig(List<Table> candidates) {
    setCandidates(candidates);
    addDefaultConfig();
  }

  @XmlElement
  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  @XmlElement
  public JoinConfig getJoinConfig() {
    return joinConfig;
  }

  public void setJoinConfig(JoinConfig joinConfig) {
    this.joinConfig = joinConfig;
  }

  @Override
  public boolean lackChildConfig() {
    return tableName.equals(EMPTY) && getSubQuery() == null && joinConfig == null;
  }

  @Override
  public RelationConfig addDefaultConfig() {
    final Class<?> random = Possibility.possibility(0.25, 0.125)
        .random(QueryConfig.class, JoinConfig.class, tableName.getClass());
    // also set candidates here
    if (random == String.class) {
      operand = TableUtil.randomTable(getCandidatesTables());
    } else if (random == QueryConfig.class) {
      setSubQuery(QueryConfig.fromQuery(getCandidatesTables()));
    } else {
      joinConfig = new JoinConfig(getCandidatesTables());
    }
    return this;
  }

  Table toTable() {
    if (operand != null) {
      return operand;
    }

    if (!tableName.equals(EMPTY)) {
      return TableUtil.getTableByName(getCandidatesTables(), tableName);
    }
    if (joinConfig != null) {
      return joinConfig.explicitJoin();
    }
    assert getSubQuery() != null;
    return SelectResult.generateQuery(getSubQuery());
  }
}
