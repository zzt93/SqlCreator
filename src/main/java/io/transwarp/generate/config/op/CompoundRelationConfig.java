package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.stmt.QueryConfig;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by zzt on 1/20/17.
 * <p>
 * <h3></h3>
 */
@XmlType(name = "compoundRelation")
public class CompoundRelationConfig extends SimpleRelationConfig {
  private static final double JOIN_OP_QUERY_POSS = 0.125;
  private static final double JOIN_OP_JOIN_POSS = 0.125;
  private ExplicitJoinConfig joinedTable;

  public CompoundRelationConfig() {
  }

  CompoundRelationConfig(List<Table> candidates) {
    addDefaultConfig(candidates, null);
  }

  @XmlElement
  public ExplicitJoinConfig getJoinedTable() {
    return joinedTable;
  }

  public void setJoinedTable(ExplicitJoinConfig joinedTable) {
    this.joinedTable = joinedTable;
  }

  @Override
  public boolean lackChildConfig() {
    return getCandidatesTables() == null
        || ((getSubQuery() == null || getSubQuery().lackChildConfig())
        && invalidTableName()
        && (!hasNested() || joinedTable.lackChildConfig()));
  }

  @Override
  public CompoundRelationConfig addDefaultConfig(List<Table> fromCandidates, List<Table> fatherStmtUse) {
    assert lackChildConfig();
    setFromCandidates(fromCandidates);

    if (getSubQuery() != null) {
      getSubQuery().addDefaultConfig(fromCandidates, fatherStmtUse);
      return this;
    }
    if (!invalidTableName()) {
      return this;
    }
    if (hasNested()) {
      joinedTable.addDefaultConfig(fromCandidates, fatherStmtUse);
      return this;
    }

    defaultChoice();
    return this;
  }

  private boolean hasNested() {
    return joinedTable != null;
  }

  @Override
  void defaultChoice() {
    final Class<?> random = Possibility.possibility(JOIN_OP_QUERY_POSS, JOIN_OP_JOIN_POSS)
        .random(QueryConfig.class, ExplicitJoinConfig.class, getTableName().getClass());
    if (random == String.class) {
      operand = TableUtil.randomTable(getCandidatesTables());
      setTableName(operand.name().get());
    } else if (random == QueryConfig.class) {
      noCopySetSubQuery(QueryConfig.fromQuery(getCandidatesTables()));
    } else {
      joinedTable = new ExplicitJoinConfig(getCandidatesTables());
    }
  }

  @Override
  Table toTable() {
    if (hasNested()) {
      return joinedTable.explicitJoin();
    }
    return super.toTable();
  }

  @Override
  public CompoundRelationConfig deepCopyTo(SetConfig t) {
    super.deepCopyTo(t);
    final CompoundRelationConfig comp = (CompoundRelationConfig) t;
    if (hasNested()) {
      comp.setJoinedTable(joinedTable.deepCopyTo(new ExplicitJoinConfig()));
    }
    return comp;
  }
}
