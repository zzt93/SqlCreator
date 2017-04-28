package io.transwarp.out.file;

import com.google.inject.Inject;
import io.transwarp.out.SqlWriter;

import java.io.PrintWriter;

/**
 * Created by zzt on 17/4/27.
 */
public class FileSqlWriter implements SqlWriter{

  private PrintWriter printWriter;

  @Inject
  public FileSqlWriter(PrintWriter printWriter) {
    this.printWriter = printWriter;
  }

  @Override
  public void println(String s) {
    printWriter.println(s);
  }

  @Override
  public void flush() {
    printWriter.flush();
  }

  @Override
  public void close() {
    printWriter.close();
  }
}
