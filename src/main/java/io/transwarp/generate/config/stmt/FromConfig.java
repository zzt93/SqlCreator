package io.transwarp.generate.config.stmt;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.op.JoinConfig;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzt on 1/17/17.
 * <p>
 * <h3></h3>
 */
public class FromConfig implements DefaultConfig<FromConfig> {

  private JoinConfig join;
  private int joinTimes;

  private List<Table> fromObj = new ArrayList<>();
  private List<Table> candidates;

  public FromConfig() {
  }

  FromConfig(List<Table> tables) {
    setCandidates(tables);
    addDefaultConfig();
  }

  @XmlElement
  public JoinConfig getJoin() {
    join.setFrom(fromObj);
    return join;
  }

  public void setJoin(JoinConfig join) {
    this.join = join;
  }

  @XmlElement
  public int getJoinTimes() {
    return joinTimes;
  }

  public void setJoinTimes(int joinTimes) {
    this.joinTimes = joinTimes;
  }

  public FromConfig setFrom(List<Table> from) {
    if (join != null) {
      join.setFrom(from);
    }
    this.fromObj = from;
    return this;
  }

  @Override
  public FromConfig setCandidates(List<Table> candidates) {
    if (join != null) {
      join.setCandidates(candidates);
    }
    this.candidates = candidates;
    return this;
  }

  private void initFromObj(List<Table> candidates) {
    if (implicitJoin()) {
      List<Table> tmp = implicitJoin(candidates);
      fromObj.addAll(tmp);
    } else {
      final JoinConfig join = getJoin();
      Table table = join.explicitJoin();
      fromObj.add(table);
    }
  }

  private List<Table> implicitJoin(List<Table> candidates) {
    // TODO generate and add subQuery
//      QueryConfig config = QueryConfig.simpleQuery(candidates);

    final int tableSize = getJoinTimes() + 1;
    List<Table> tmp = new ArrayList<>(tableSize);
    for (int i = 0; i < tableSize; i++) {
      tmp.add(TableUtil.deepCopy(TableUtil.randomTable(candidates)).setAlias(TableUtil.nextAlias()));
    }
    return tmp;
  }

  public List<Table> getFromObj() {
    if (fromObj.isEmpty()) {
      initFromObj(candidates);
    }
    return fromObj;
  }

  @Override
  public boolean lackChildConfig() {
    return join == null && joinTimes == 0;
  }

  @Override
  public FromConfig addDefaultConfig() {
    joinTimes = 1;
    return this;
  }

  private boolean implicitJoin() {
    return join == null;
  }
}
