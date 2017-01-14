package io.transwarp.parse.xml;

import io.transwarp.parse.ParserSource;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class XMLParserSource implements ParserSource{


  private final String xmlFile;

  XMLParserSource(String xmlFile) {
    this.xmlFile = xmlFile;
  }

  @Override
  public String getSource() {
    return xmlFile;
  }
}
