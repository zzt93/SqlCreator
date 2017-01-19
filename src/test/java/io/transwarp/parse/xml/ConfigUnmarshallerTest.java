package io.transwarp.parse.xml;

import io.transwarp.generate.config.GlobalConfig;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

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
    final GlobalConfig parse = configUnmarshaller.parse(new XMLParserSource("src/main/resources/template.xml"));
    System.out.println();
  }

  @Test
  public void generate() throws IOException, JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(GlobalConfig.class);
    SchemaOutputResolver sor = new MySchemaOutputResolver();
    jaxbContext.generateSchema(sor);
  }

  private class MySchemaOutputResolver extends SchemaOutputResolver {
    @Override
    public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
      File file = new File(suggestedFileName);
      StreamResult result = new StreamResult(file);
      result.setSystemId(file.toURI().toURL().toString());
      return result;
    }
  }
}