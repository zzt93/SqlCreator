package io.transwarp.generate.config.stmt;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.op.ExplicitJoinConfig;
import io.transwarp.generate.config.op.ImplicitJoinConfig;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zzt on 1/17/17.
 * <p>
 * <h3></h3>
 */
public class FromConfig implements DefaultConfig<FromConfig> {

  private ExplicitJoinConfig explicitJoin;
  private ImplicitJoinConfig implicitJoin;
  private int joinTimes;

  private List<Table> fromObj = new ArrayList<>();
  private List<Table> candidates;

  @XmlElement
  public ExplicitJoinConfig getExplicitJoin() {
    return explicitJoin;
  }

  public void setExplicitJoin(ExplicitJoinConfig explicitJoin) {
    this.explicitJoin = explicitJoin;
  }

  @XmlElement
  public int getJoinTimes() {
    return joinTimes;
  }

  public void setJoinTimes(int joinTimes) {
    this.joinTimes = joinTimes;
  }

  @XmlElement
  public ImplicitJoinConfig getImplicitJoin() {
    return implicitJoin;
  }

  public void setImplicitJoin(ImplicitJoinConfig implicitJoin) {
    this.implicitJoin = implicitJoin;
  }

  public FromConfig setStmtUse(List<Table> stmtUse) {
    // only need candidates, from is generated
    throw new NotImplementedException();
  }

  @Override
  public FromConfig setFromCandidates(List<Table> fromCandidates) {
    this.candidates = fromCandidates;
    return this;
  }

  private void initFromObj(List<Table> candidates) {
    if (joinTimesSet()) {
      assert implicitJoin == null;
      implicitJoin = new ImplicitJoinConfig(joinTimes).addDefaultConfig(candidates, null);
      fromObj.addAll(implicitJoin.implicitJoin());
    } else if (implicitJoin != null) {
      fromObj.addAll(implicitJoin.implicitJoin());
    } else {
      final ExplicitJoinConfig join = getExplicitJoin();
      Table table = join.explicitJoin();
      fromObj.add(table);
    }
  }

  public List<Table> getFromObj() {
    return Collections.unmodifiableList(fromObj);
  }

  @Override
  public boolean lackChildConfig() {
    return lackJoin(implicitJoin) && lackJoin(explicitJoin) && (joinTimes == 0);
  }

  private boolean lackJoin(DefaultConfig config) {
    return config == null || config.lackChildConfig();
  }

  @Override
  public FromConfig addDefaultConfig(List<Table> fromCandidates, List<Table> fatherStmtUse) {
    setFromCandidates(fromCandidates);

    // check children first
    if (explicitJoin != null) {
      explicitJoin.addDefaultConfig(fromCandidates, null);
    } else if (implicitJoin != null) {
      implicitJoin.addDefaultConfig(fromCandidates, null);
    } else {
      joinTimes = 1;
    }
    // generate father node then
    if (fromObj.isEmpty()) {
      initFromObj(this.candidates);
    }

    return this;
  }

  private boolean joinTimesSet() {
    return joinTimes != 0;
  }
}
