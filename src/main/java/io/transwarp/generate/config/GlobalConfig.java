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

  public static final int VAR_ARGS_MAX_LEN = 5;

  private static int randomBitsMaxLen = 64;
  private static int randomListMaxLen = 10;
  private static int randomStrMaxLen = 100;

  private static Dialect cmp = Dialect.INCEPTOR;
  private static Dialect base = Dialect.ORACLE;

  private List<PerTestConfig> perTestConfigs;

  public static Dialect getCmp() {
    return cmp;
  }

  public static Dialect getBase() {
    return base;
  }

  public static int getRandomListMaxLen() {
    return randomListMaxLen;
  }

  public static int getRandomBitsMaxLen() {
    return randomBitsMaxLen;
  }

  public static int getRandomStrMaxLen() {
    return randomStrMaxLen;
  }

  public static <T extends DefaultConfig<T>> void checkConfig(DefaultConfig<T> defaultConfig, List<Table> candidates, List<Table> from) {
    if (defaultConfig != null) {
      if (defaultConfig.lackChildConfig()) {
        defaultConfig.addDefaultConfig(candidates, from);
      }
    }
  }

  public GlobalConfig setRandomBitsMaxLen(int randomBitsMaxLen) {
    GlobalConfig.randomBitsMaxLen = randomBitsMaxLen;
    return this;
  }

  public GlobalConfig setRandomListMaxLen(int randomListMaxLen) {
    GlobalConfig.randomListMaxLen = randomListMaxLen;
    return this;
  }

  public GlobalConfig setRandomStrMaxLen(int randomStrMaxLen) {
    GlobalConfig.randomStrMaxLen = randomStrMaxLen;
    return this;
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
      final String[] generate = queryConfig.generate(getCmpBase());
      for (Dialect dialect : getCmpBase()) {
        PrintWriter oracle = getPrintWriter(dirs.get(dialect), queryConfig.getId());
        oracle.println(generate[dialect.ordinal()]);
      }
    }
  }

  private PrintWriter getPrintWriter(Path dir, String prefix) throws FileNotFoundException {
    final Path path = Paths.get(dir.toString(), prefix + ".sql");
    return new PrintWriter(new OutputStreamWriter(new FileOutputStream(path.toString(), false)));
  }
}
