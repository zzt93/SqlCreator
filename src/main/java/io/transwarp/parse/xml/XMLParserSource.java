package io.transwarp.parse.xml;

import io.transwarp.parse.ParserSource;

import java.io.InputStream;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class XMLParserSource implements ParserSource{


  private final InputStream xmlFile;

  public XMLParserSource(InputStream xmlFile) {
    this.xmlFile = xmlFile;
  }

  @Override
  public InputStream getSource() {
    return xmlFile;
  }
}
