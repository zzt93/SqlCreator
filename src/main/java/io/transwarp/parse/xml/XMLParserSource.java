package io.transwarp.parse.xml;

import com.google.inject.Inject;
import io.transwarp.parse.ParserSource;

import java.io.InputStream;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class XMLParserSource implements ParserSource{


  private final InputStream xmlFile;

  @Inject
  public XMLParserSource(InputStream xmlFile) {
    this.xmlFile = xmlFile;
  }

  @Override
  public InputStream getSource() {
    return xmlFile;
  }
}
