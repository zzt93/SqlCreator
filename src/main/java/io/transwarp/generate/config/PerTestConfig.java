package io.transwarp.generate.config;

import io.transwarp.generate.config.stmt.StmtConfig;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class PerTestConfig {

  private List<StmtConfig> stmtConfigs;
  private boolean transaction;
  private String tableDdlFile;

  @XmlElement(name = "query")
  public List<StmtConfig> getStmtConfigs() {
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

  public String getTableDdlFile() {
    return tableDdlFile;
  }

  public PerTestConfig setTableDdlFile(String tableDdlFile) {
    this.tableDdlFile = tableDdlFile;
    return this;
  }
}
