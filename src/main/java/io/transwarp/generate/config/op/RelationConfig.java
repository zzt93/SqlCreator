package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.stmt.select.SelectResult;

import javax.xml.bind.annotation.XmlAttribute;
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
  private static final double JOIN_OP_QUERY_POSS = 0.125;
  private static final double JOIN_OP_JOIN_POSS = 0.125;
  private String alias = TableUtil.INVALID_ALIAS;
  private String tableName = TableUtil.INVALID_ALIAS;
  private ExplicitJoinConfig joinedTable;

  /*
  ------------- generated field --------------
   */
  /**
   * the mapped table of tableName
   *
   * @see #tableName
   */
  private Table operand;

  public RelationConfig() {
  }

  RelationConfig(List<Table> candidates) {
    addDefaultConfig(candidates, null);
  }

  @XmlElement
  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  @XmlElement
  public ExplicitJoinConfig getJoinedTable() {
    return joinedTable;
  }

  public void setJoinedTable(ExplicitJoinConfig joinedTable) {
    this.joinedTable = joinedTable;
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
    return getCandidatesTables() == null
        || (
        invalidTableName()
            && (getSubQuery() == null || getSubQuery().lackChildConfig())
            && (joinedTable == null || joinedTable.lackChildConfig()));
  }

  @Override
  public RelationConfig addDefaultConfig(List<Table> candidates, List<Table> from) {
    assert lackChildConfig();
    setCandidates(candidates);

    if (joinedTable != null) {
      joinedTable.addDefaultConfig(candidates, from);
      return this;
    }
    if (getSubQuery() != null) {
      getSubQuery().addDefaultConfig(candidates, from);
      return this;
    }
    if (!invalidTableName()) {
      return this;
    }

    final Class<?> random = Possibility.possibility(JOIN_OP_QUERY_POSS, JOIN_OP_JOIN_POSS)
        .random(QueryConfig.class, ExplicitJoinConfig.class, tableName.getClass());
    if (random == String.class) {
      operand = TableUtil.randomTable(getCandidatesTables());
      tableName = operand.name().get();
    } else if (random == QueryConfig.class) {
      setSubQuery(QueryConfig.fromQuery(getCandidatesTables()));
    } else {
      joinedTable = new ExplicitJoinConfig(getCandidatesTables());
    }
    return this;
  }

  private boolean invalidTableName() {
    return tableName.equals(TableUtil.INVALID_ALIAS);
  }


  Table toTable() {
    if (!invalidTableName()) {
      if (operand == null) {
        operand = TableUtil.getTableByName(getCandidatesTables(), tableName);
      }
      return operand.toTable(alias);
    }
    if (joinedTable != null) {
      return joinedTable.explicitJoin();
    }
    final QueryConfig subQuery = getSubQuery();
    assert subQuery != null;
    subQuery.getSelect().setUseStar(BiChoicePossibility.IMPOSSIBLE);
    return SelectResult.generateQuery(subQuery).toTable(alias);
  }
}
