package io.transwarp.out;

import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
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
}
