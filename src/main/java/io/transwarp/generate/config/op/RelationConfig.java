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
  private JoinConfig joinedTable;

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
  public JoinConfig getJoinedTable() {
    return joinedTable;
  }

  public void setJoinedTable(JoinConfig joinedTable) {
    this.joinedTable = joinedTable;
  }

  @Override
  public boolean lackChildConfig() {
    return tableName.equals(EMPTY)
        && (getSubQuery() == null || getSubQuery().lackChildConfig())
        && (joinedTable == null || joinedTable.lackChildConfig());
  }

  @Override
  public RelationConfig addDefaultConfig() {
    assert lackChildConfig();
    if (joinedTable != null) {
      joinedTable.addDefaultConfig();
      return this;
    }
    if (getSubQuery() != null) {
      getSubQuery().addDefaultConfig();
      return this;
    }

    final Class<?> random = Possibility.possibility(0.25, 0.125)
        .random(QueryConfig.class, JoinConfig.class, tableName.getClass());
    // also set candidates here
    if (random == String.class) {
      operand = TableUtil.randomTable(getCandidatesTables());
      tableName = operand.name().get();
    } else if (random == QueryConfig.class) {
      setSubQuery(QueryConfig.fromQuery(getCandidatesTables()));
    } else {
      joinedTable = new JoinConfig(getCandidatesTables());
    }
    return this;
  }

  @Override
  public SetConfig setCandidates(List<Table> candidates) {
    if (joinedTable != null) {
      joinedTable.setCandidates(candidates);
    }
    return super.setCandidates(candidates);
  }

  Table toTable() {
    if (!tableName.equals(EMPTY)) {
      if (operand != null) {
        return operand;
      }
      return TableUtil.getTableByName(getCandidatesTables(), tableName);
    }
    if (joinedTable != null) {
      return joinedTable.explicitJoin();
    }
    assert getSubQuery() != null;
    return SelectResult.generateQuery(getSubQuery());
  }
}
