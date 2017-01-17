package io.transwarp.parse.xml;

import io.transwarp.generate.config.GlobalConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class ConfigUnmarshallerTest {

  private ConfigUnmarshaller configUnmarshaller;

  @Before
  public void setUp() throws Exception {
    configUnmarshaller = new ConfigUnmarshaller();
  }

  @Test
  public void parse() throws Exception {
    final GlobalConfig parse = configUnmarshaller.parse(new XMLParserSource("src/main/resources/template.xml"));
    System.out.println();
  }

}