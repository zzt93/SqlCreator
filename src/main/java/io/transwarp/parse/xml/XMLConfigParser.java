package io.transwarp.parse.xml;

import io.transwarp.generate.config.PerGenerationConfig;
import io.transwarp.parse.ConfigParser;
import io.transwarp.parse.ParseException;
import io.transwarp.parse.ParserSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Iterator;

/**
 * Created by zzt on 12/8/16.
 * <p>
 * <h3></h3>
 */
public class XMLConfigParser implements ConfigParser {

  private static final String ENCODING = "UTF-8";
  private ParseException e;
  private Document doc;

  private XMLConfigParser(ParserSource source) throws IOException {
    parseXML(source.getSource());
  }

  private void parseXML(String filename) throws IOException {
    DocumentBuilder db = getDocumentBuilder();
    try {
      doc = db.parse(new File(filename));
    } catch (SAXException e) {
      this.e = new ParseException(e);
    }
  }

  private static final String JAXP_SCHEMA_SOURCE =
      "http://java.sun.com/xml/jaxp/properties/schemaSource";
  private static final String JAXP_SCHEMA_LANGUAGE =
      "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
  private static final String W3C_XML_SCHEMA =
      "http://www.w3.org/2001/XMLSchema";

  private DocumentBuilder getDocumentBuilder() throws UnsupportedEncodingException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setValidating(true);
    dbf.setNamespaceAware(true);
    dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
    dbf.setAttribute(JAXP_SCHEMA_SOURCE, new File("src/main/resources/define.xsd"));

    DocumentBuilder db = null;
    try {
      db = dbf.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
      assert false;
    }
    OutputStreamWriter errorWriter = new OutputStreamWriter(System.err, ENCODING);
    db.setErrorHandler(new XMLParseErrorHandler(new PrintWriter(errorWriter, true)));
    return db;
  }

  @Override
  public Iterator<PerGenerationConfig> parseEach() throws ParseException {
    checkState();
    return null;
  }

  @Override
  public void parseGlobal() throws ParseException {
    checkState();
  }

  private void checkState() throws ParseException {
    if (e != null) {
      throw e;
    }
  }

  static class Builder implements ConfigParser.ConfigParserBuilder {

    @Override
    public ConfigParser build(ParserSource parserSource) throws IOException {
      return new XMLConfigParser(parserSource);
    }
  }
}
