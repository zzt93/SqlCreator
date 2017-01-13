package io.transwarp.parse.xml;

import io.transwarp.generate.config.PerGenerationConfig;
import io.transwarp.parse.ConfigParser;
import io.transwarp.parse.ParseException;
import io.transwarp.parse.ParserSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
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

  private void validateXML() throws SAXException, IOException {
    final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    URL schemaURL = Paths.get("define.xsd").toUri().toURL();
    Schema schema = sf.newSchema(schemaURL);
    Validator validator = schema.newValidator();
    Source xml = new DOMSource();
    validator.validate(xml);
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

  private DocumentBuilder getDocumentBuilder() throws UnsupportedEncodingException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setValidating(false);
    dbf.setNamespaceAware(true);
    dbf.setAttribute(JAXP_SCHEMA_SOURCE, new File("define.xsd"));

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

  class Builder implements ConfigParser.ConfigParserBuilder {

    @Override
    public ConfigParser build(ParserSource parserSource) throws IOException {
      return new XMLConfigParser(parserSource);
    }
  }
}
