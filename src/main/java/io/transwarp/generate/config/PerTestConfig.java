package io.transwarp.generate.config;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.op.SetOperatorConfig;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.config.stmt.StmtConfig;
import io.transwarp.generate.config.stmt.UpdateStmtConfig;
import io.transwarp.parse.sql.DDLParser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class PerTestConfig {

  private List<StmtConfig> stmtConfigs = new ArrayList<>();
  private List<SetOperatorConfig> setOperatorConfigs;
  private boolean transaction;
  private Dialect dialect;
  private String tableDdlFile;
  private int threads;

  @XmlElement(name = "setOperator", type = SetOperatorConfig.class)
  public List<SetOperatorConfig> getSetOperatorConfigs() {
    return setOperatorConfigs;
  }

  public void setSetOperatorConfigs(List<SetOperatorConfig> setOperatorConfigs) {
    this.setOperatorConfigs = setOperatorConfigs;
  }

  @XmlElements({
      @XmlElement(name = "query", type = QueryConfig.class),
      @XmlElement(name = "updateStmt", type = UpdateStmtConfig.class)
  })
  public List<StmtConfig> getStmtConfigs() {
    for (StmtConfig stmtConfig : stmtConfigs) {
      TestsConfig.checkConfig(stmtConfig, getCandidates(), null);
    }
    return stmtConfigs;
  }

  public PerTestConfig setStmtConfigs(List<StmtConfig> stmtConfigs) {
    this.stmtConfigs = stmtConfigs;
    return this;
  }

  public boolean isTransaction() {
    return transaction;
  }

  public PerTestConfig setTransaction(boolean transaction) {
    this.transaction = transaction;
    return this;
  }

  @XmlAttribute
  public String getTableDdlFile() {
    return tableDdlFile;
  }

  public PerTestConfig setTableDdlFile(String tableDdlFile) {
    this.tableDdlFile = tableDdlFile;
    return this;
  }

  @XmlAttribute
  public Dialect getDialect() {
    return dialect;
  }

  public PerTestConfig setDialect(Dialect dialect) {
    this.dialect = dialect;
    return this;
  }

  private List<Table> candidates;

  private List<Table> getCandidates() {
    if (candidates == null) {
      candidates = Collections.unmodifiableList(DDLParser.getTable(tableDdlFile, dialect));
    }
    return candidates;
  }

  @XmlAttribute
  public int getThreads() {
    return threads;
  }

  public void setThreads(int threads) {
    this.threads = threads;
  }
}
