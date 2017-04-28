package io.transwarp.out;

/**
 * Created by zzt on 17/4/27.
 */
public interface SqlWriter {

  void println(String s);

  void flush();
  void close();
}
