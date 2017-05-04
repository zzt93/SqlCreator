package io.transwarp.out;

import io.transwarp.db_specific.base.Dialect;

import java.util.EnumMap;

/**
 * Created by zzt on 17/4/27.
 */
public interface SqlWriter {

  void println(EnumMap<Dialect, String> s);

  void flush();
  void close();
}
