package io.transwarp.generate.config;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.config.stmt.StmtConfig;
import io.transwarp.out.SqlWriter;
import io.transwarp.out.file.SqlWriterGenerator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
@XmlRootElement(name = "tests")
public class TestsConfig {

  private static Dialect cmp = Dialect.INCEPTOR;
  private static Dialect base = Dialect.ORACLE;

  private List<PerTestConfig> perTestConfigs;

  public static Dialect getCmp() {
    return cmp;
  }

  public TestsConfig setCmp(Dialect cmp) {
    TestsConfig.cmp = cmp;
    return this;
  }

  public static Dialect getBase() {
    return base;
  }

  public TestsConfig setBase(Dialect base) {
    TestsConfig.base = base;
    return this;
  }

  public static <T extends DefaultConfig<T>> void checkConfig(DefaultConfig<T> defaultConfig, List<Table> candidates, List<Table> from) {
    if (defaultConfig != null) {
      if (defaultConfig.lackChildConfig()) {
        defaultConfig.addDefaultConfig(candidates, from);
      }
    }
  }

  public static Dialect[] getCmpBase() {
    return new Dialect[]{getCmp(), getBase()};
  }

  @XmlElement(name = "test")
  public List<PerTestConfig> getPerTestConfigs() {
    return perTestConfigs;
  }

  public TestsConfig setPerTestConfigs(List<PerTestConfig> perTestConfigs) {
    this.perTestConfigs = perTestConfigs;
    return this;
  }

  public List<QueryConfig> getQueries() {
    List<QueryConfig> list = new ArrayList<>();
    for (PerTestConfig perTestConfig : getPerTestConfigs()) {
      for (StmtConfig stmtConfig : perTestConfig.getStmtConfigs()) {
        if (stmtConfig instanceof QueryConfig) {
          list.add((QueryConfig) stmtConfig);
        }
      }
    }
    return list;
  }

  public void generate(SqlWriterGenerator generator) throws FileNotFoundException {
    // now, only with queries
    for (QueryConfig queryConfig : getQueries()) {
      if (queryConfig.getNum() == 0) {
        continue;
      }
      Dialect[] cmpBase = getCmpBase();
      SqlWriter sqlWriter = generator.file(queryConfig.getId());
      for (int i = 0; i < queryConfig.getNum(); i++) {
        final EnumMap<Dialect, String> generate = queryConfig.generate(cmpBase);
        sqlWriter.println(generate);
      }
      sqlWriter.flush();
      sqlWriter.close();
    }
  }

}
