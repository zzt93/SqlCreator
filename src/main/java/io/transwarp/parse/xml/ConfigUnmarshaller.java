package io.transwarp.parse.xml;

import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.parse.ConfigParser;
import io.transwarp.parse.ParserSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class ConfigUnmarshaller implements ConfigParser {

  public GlobalConfig parse(ParserSource parserSource) throws IOException, ValidationException {
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      Schema schema = sf.newSchema(new File("src/main/resources/define.xsd"));

      JAXBContext jc = JAXBContext.newInstance(GlobalConfig.class);

      Unmarshaller unmarshaller = jc.createUnmarshaller();
      unmarshaller.setSchema(schema);
      unmarshaller.setEventHandler(new PrintInfoHandler());
      return (GlobalConfig) unmarshaller.unmarshal(new File(parserSource.getSource()));
    } catch (SAXException | JAXBException e) {
      throw new ValidationException(e);
    }
  }

}
