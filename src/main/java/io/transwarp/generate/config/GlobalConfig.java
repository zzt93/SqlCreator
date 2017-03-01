package io.transwarp.generate.config;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.config.stmt.StmtConfig;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
@XmlRootElement(name = "tests")
public class GlobalConfig {

  private static Dialect cmp = Dialect.INCEPTOR;
  private static Dialect base = Dialect.ORACLE;

  private List<PerTestConfig> perTestConfigs;

  public static Dialect getCmp() {
    return cmp;
  }

  public static Dialect getBase() {
    return base;
  }

  public static <T extends DefaultConfig<T>> void checkConfig(DefaultConfig<T> defaultConfig, List<Table> candidates, List<Table> from) {
    if (defaultConfig != null) {
      if (defaultConfig.lackChildConfig()) {
        defaultConfig.addDefaultConfig(candidates, from);
      }
    }
  }

  public static <T extends DefaultConfig<T>> void resetConfig(DefaultConfig<T> defaultConfig) {
    if (defaultConfig != null) {
      defaultConfig.setStmtUse(null);
    }
  }

  public GlobalConfig setCmp(Dialect cmp) {
    GlobalConfig.cmp = cmp;
    return this;
  }

  public GlobalConfig setBase(Dialect base) {
    GlobalConfig.base = base;
    return this;
  }

  @XmlElement(name = "test")
  public List<PerTestConfig> getPerTestConfigs() {
    return perTestConfigs;
  }

  public GlobalConfig setPerTestConfigs(List<PerTestConfig> perTestConfigs) {
    this.perTestConfigs = perTestConfigs;
    return this;
  }

  public static Dialect[] getCmpBase() {
    return new Dialect[]{getCmp(), getBase()};
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

  public void generate(EnumMap<Dialect, Path> dirs) throws FileNotFoundException {
    // now, only with queries
    for (QueryConfig queryConfig : getQueries()) {
      if (queryConfig.getNum() == 0) {
        continue;
      }
      EnumMap<Dialect, PrintWriter> writers = getPrintWriter(dirs, queryConfig.getId());
      for (int i = 0; i < queryConfig.getNum(); i++) {
        final EnumMap<Dialect, String> generate = queryConfig.generate(getCmpBase());
        for (Dialect dialect : getCmpBase()) {
          writers.get(dialect).println(generate.get(dialect));
        }
      }
      for (PrintWriter printWriter : writers.values()) {
        printWriter.flush();
        printWriter.close();
      }
    }
  }

  private EnumMap<Dialect, PrintWriter> getPrintWriter(EnumMap<Dialect, Path> dirs, String prefix) throws FileNotFoundException {
    final Dialect[] dialects = getCmpBase();
    EnumMap<Dialect, PrintWriter> res = new EnumMap<>(Dialect.class);
    for (Dialect dialect : dialects) {
      final Path dir = dirs.get(dialect);
      final Path path = Paths.get(dir.toString(), prefix + ".sql");
      res.put(dialect, new PrintWriter(new OutputStreamWriter(new FileOutputStream(path.toString(), false))));
    }
    return res;
  }
}
