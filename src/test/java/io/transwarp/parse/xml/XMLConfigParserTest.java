package io.transwarp.parse.xml;

import io.transwarp.parse.ConfigParser;
import org.junit.Test;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class XMLConfigParserTest {

  @Test
  public void parseEach() throws Exception {
    ConfigParser parser = new XMLConfigParser(new XMLParserSource("src/main/resources/template.xml"));
    parser.parse(new XMLParserSource("src/main/resources/template.xml"));
  }

}