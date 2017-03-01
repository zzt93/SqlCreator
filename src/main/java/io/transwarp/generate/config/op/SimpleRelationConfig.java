package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.config.stmt.QueryConfig;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by zzt on 2/24/17.
 * <p>
 * <h3></h3>
 */
public class SimpleRelationConfig extends SetConfig {
  private static final double JOIN_OP_QUERY_POSS = 0.25;
  private String tableName = TableUtil.INVALID_ALIAS;
  /**
   * the mapped table of tableName
   *
   * @see #tableName
   */
  protected Table operand;

  @XmlElement
  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  protected boolean invalidTableName() {
    return tableName.equals(TableUtil.INVALID_ALIAS);
  }

  @Override
  Table toTable() {
    if (!invalidTableName()) {
      if (operand == null) {
        operand = TableUtil.getTableByName(getCandidatesTables(), getTableName());
      }
      return operand.toTable(getAlias());
    }
    return super.toTable();
  }

  @Override
  public boolean lackChildConfig() {
    return getCandidatesTables() == null
        || ((getSubQuery() == null || getSubQuery().lackChildConfig())
        && invalidTableName());
  }

  @Override
  public SimpleRelationConfig addDefaultConfig(List<Table> fromCandidates, List<Table> fatherStmtUse) {
    setFromCandidates(fromCandidates);

    if (getSubQuery() != null) {
      getSubQuery().addDefaultConfig(fromCandidates, fatherStmtUse);
      return this;
    }
    if (!invalidTableName()) {
      return this;
    }

    defaultChoice();
    return this;
  }

  @Override
  void defaultChoice() {
    final Class<?> random = BiChoicePossibility.possibility(JOIN_OP_QUERY_POSS)
        .random(QueryConfig.class, getTableName().getClass());
    if (random == String.class) {
      operand = TableUtil.randomTable(getCandidatesTables());
      setTableName(operand.name().get());
    } else if (random == QueryConfig.class) {
      setSubQuery(QueryConfig.fromQuery(getCandidatesTables()));
    }
  }
}
