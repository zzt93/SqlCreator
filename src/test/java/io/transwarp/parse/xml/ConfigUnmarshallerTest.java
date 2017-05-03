package io.transwarp.parse.xml;

import io.transwarp.generate.config.TestsConfig;
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
import java.io.InputStream;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class ConfigUnmarshallerTest {

  private ConfigUnmarshaller configUnmarshaller = new ConfigUnmarshaller();

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void parse() throws Exception {
    final TestsConfig parse = getGlobalConfig("template.xml");
    System.out.println();
  }

  private TestsConfig getGlobalConfig(String file) throws IOException, ValidationException {
    InputStream inputPath = ClassLoader.getSystemResourceAsStream(file);
    return configUnmarshaller.parse(new XMLParserSource(inputPath));
  }

  @Test
  public void generationError() throws Exception {
    TestsConfig parse = getGlobalConfig("generationError.xml");
    for (PerTestConfig perTestConfig : parse.getPerTestConfigs()) {
      for (StmtConfig stmtConfig : perTestConfig.getStmtConfigs()) {
        boolean rightError = false;
        try {
          stmtConfig.generate(TestsConfig.getCmpBase());
        } catch (IllegalArgumentException e) {
          final String msg = e.getMessage();
          System.out.println(msg);
          rightError = msg.contains("SubQuery in where statement has more than one column")
              || msg.contains("SubQuery in select statement ")
              || msg.contains("Illegal table name")
          ;
        }
        Assert.assertTrue(rightError);
      }
    }
  }

  @Test
  public void validationError() throws Exception {
//          ("The content of element 'operand' is not complete. One of '{subQuery, tableName}' is expected.");
    errorTest("xsdValidationError.xml", ValidationException.class);
  }

  @Test
  public void parseError() throws Exception {
//    errorTest("parseError.xml", IllegalArgumentException.class);
  }

  private void errorTest(String file, Class<? extends Exception> exceptionClass) throws IOException {
    boolean rightError = false;
    try {
      getGlobalConfig(file);
    } catch (Exception e) {
      e.printStackTrace();
      if (exceptionClass.isInstance(e)) {
        rightError = true;
      }
    }
    Assert.assertTrue(rightError);
  }

  @Test
  public void generate() throws IOException, JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(TestsConfig.class);
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