package io.transwarp.parse.xml;

import io.transwarp.parse.ConfigParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class XMLConfigParserTest {

  private ConfigParser parser;

  @Before
  public void setUp() throws Exception {
    try {
      parser = new XMLConfigParser.Builder().build(new XMLParserSource("src/main/resources/template.xml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void parseEach() throws Exception {
    parser.parseEach();
  }

  @Test
  public void parseGlobal() throws Exception {
    parser.parseGlobal();
  }

}