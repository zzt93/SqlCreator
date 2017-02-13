package io.transwarp.parse.xml;

import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.config.PerTestConfig;
import io.transwarp.generate.config.stmt.StmtConfig;
import org.junit.Assert;
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

  private static ConfigUnmarshaller configUnmarshaller = new ConfigUnmarshaller();

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void parse() throws Exception {
    final GlobalConfig parse = getGlobalConfig("src/main/resources/template.xml");
    System.out.println();
  }

  private static GlobalConfig getGlobalConfig(String file) throws IOException, ValidationException {
    return configUnmarshaller.parse(new XMLParserSource(file));
  }

  public static GlobalConfig getGlobalConfig() throws IOException, ValidationException {
    return configUnmarshaller.parse(new XMLParserSource("src/main/resources/test.xml"));
  }

  @Test
  public void generationError() throws Exception {
    boolean rightError = false;
    GlobalConfig parse = getGlobalConfig("src/test/resources/generationError.xml");
    for (PerTestConfig perTestConfig : parse.getPerTestConfigs()) {
      for (StmtConfig stmtConfig : perTestConfig.getStmtConfigs()) {
        try {
          stmtConfig.generate(GlobalConfig.getCmpBase());
        } catch (IllegalArgumentException e) {
          final String msg = e.getMessage();
          rightError = msg.contains("Illegal table name:")
              || msg.contains("SubQuery in where statement has more than one column");
          Assert.assertTrue(rightError);
        }
      }
    }
  }

  @Test
  public void parseError() throws Exception {
    boolean rightError = false;
    try {
      getGlobalConfig("src/test/resources/parseError.xml");
    } catch (ValidationException e) {
//          ("The content of element 'operand' is not complete. One of '{subQuery, tableName}' is expected.");
      e.printStackTrace();
      rightError = true;
    }
    Assert.assertTrue(rightError);
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