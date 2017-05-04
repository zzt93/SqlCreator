package io.transwarp.out.file;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.out.SqlWriter;

import java.nio.file.Path;
import java.util.EnumMap;
import java.util.HashMap;

/**
 * Created by zzt on 17/5/3.
 */
public class SqlWriterGenerator {

  private HashMap<String, SqlWriter> writers = new HashMap<>();
  private EnumMap<Dialect, Path> baseDir;

  public SqlWriterGenerator(EnumMap<Dialect, Path> baseDir) {
    this.baseDir = baseDir;
  }


  public SqlWriter file(String id) {
    if (writers.containsKey(id)) {
      return writers.get(id);
    }
    FileSqlWriter fileSqlWriter = new FileSqlWriter(baseDir, id);
    writers.put(id, fileSqlWriter);
    return fileSqlWriter;
  }
}
