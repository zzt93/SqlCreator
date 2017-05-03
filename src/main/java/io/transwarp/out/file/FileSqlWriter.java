package io.transwarp.out.file;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.out.SqlWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;

/**
 * Created by zzt on 17/4/27.
 */
public class FileSqlWriter implements SqlWriter {


  private EnumMap<Dialect, PrintWriter> printWriters = new EnumMap<>(Dialect.class);

  FileSqlWriter(EnumMap<Dialect, Path> dirs, String fileName) {
    try {
      for (Dialect dialect : dirs.keySet()) {
        final Path name = Paths.get(dirs.get(dialect).toString(), fileName + ".sql");
        printWriters.put(dialect, new PrintWriter(new OutputStreamWriter(new FileOutputStream(name.toFile(), false))));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void println(EnumMap<Dialect, String> s) {
    for (Dialect dialect : printWriters.keySet()) {
      printWriters.get(dialect).println(s.get(dialect));
    }
  }

  @Override
  public void flush() {
    for (Dialect dialect : printWriters.keySet()) {
      printWriters.get(dialect).flush();
    }
  }

  @Override
  public void close() {
    for (Dialect dialect : printWriters.keySet()) {
      printWriters.get(dialect).close();
    }
  }
}
