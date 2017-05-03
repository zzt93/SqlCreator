package io.transwarp;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.TestsConfig;
import io.transwarp.out.OutputConfig;
import io.transwarp.out.file.SqlWriterGenerator;
import io.transwarp.parse.cl.CLParser;
import io.transwarp.parse.xml.ConfigUnmarshaller;
import io.transwarp.parse.xml.ValidationException;
import io.transwarp.parse.xml.XMLParserSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.EnumMap;

/**
 * Created by zzt on 2/15/17.
 * <p>
 * <h3></h3>
 */
public class Main {

  private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public static void main(String[] args) throws IOException, ValidationException {
    OutputConfig.configureLog();

    try {
      final CLParser clParser = new CLParser(args);
      InputStream xmlFile = clParser.getInput();
      EnumMap<Dialect, Path> map = clParser.getOutputDir();
      SqlWriterGenerator generator = OutputConfig.configureSql(map);

      final TestsConfig testsConfig = new ConfigUnmarshaller().parse(new XMLParserSource(xmlFile));
      testsConfig.generate(generator);
    } catch (Exception e){
      e.printStackTrace();
      logger.error("Fail to generate SQL", e);
    }
  }

}
