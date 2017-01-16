package io.transwarp.parse.xml;

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
    configUnmarshaller.parse(new XMLParserSource("src/main/resources/template.xml"));
  }

}