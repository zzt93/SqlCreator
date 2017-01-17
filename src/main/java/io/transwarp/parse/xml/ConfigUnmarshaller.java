package io.transwarp.parse.xml;

import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.parse.ConfigParser;
import io.transwarp.parse.ParserSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class ConfigUnmarshaller implements ConfigParser {

  public GlobalConfig parse(ParserSource parserSource) throws IOException, ValidationException {
    SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setNamespaceAware(true);

    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      Schema schema = sf.newSchema(new File("src/main/resources/define.xsd"));
      spf.setSchema(schema);
      JAXBContext jc = JAXBContext.newInstance(GlobalConfig.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();

      XMLReader xmlReader = spf.newSAXParser().getXMLReader();
      SAXSource source = new SAXSource(xmlReader, new InputSource(new FileInputStream(parserSource.getSource())));
      unmarshaller.setEventHandler(new PrintInfoHandler());
      return (GlobalConfig) unmarshaller.unmarshal(source);

    } catch (SAXException | JAXBException | ParserConfigurationException e) {
      throw new ValidationException(e);
    }
  }

}
