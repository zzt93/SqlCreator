package io.transwarp.out;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.out.file.SqlWriterGenerator;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Properties;

/**
 * Created by zzt on 17/5/3.
 */
public class OutputConfig {


  public static void configureLog() {
    // slf4j
    Properties props = System.getProperties();
    try {
      props.load(ClassLoader.getSystemResourceAsStream("slf4j.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    // log4j
    PropertyConfigurator.configure(ClassLoader.getSystemResourceAsStream("log4j.properties"));
  }

  public static SqlWriterGenerator configureSql(EnumMap<Dialect, Path> map) {
    return new SqlWriterGenerator(map);
  }
}
