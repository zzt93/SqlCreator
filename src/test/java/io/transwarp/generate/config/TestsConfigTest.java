package io.transwarp.generate.config;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.util.Strs;
import io.transwarp.out.OutputConfig;
import io.transwarp.parse.cl.CLParser;
import io.transwarp.parse.xml.ConfigUnmarshaller;
import io.transwarp.parse.xml.XMLParserSource;
import org.junit.Test;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.EnumMap;

/**
 * Created by zzt on 2/28/17.
 * <p>
 * <h3></h3>
 */
public class TestsConfigTest {
  @Test
  public void generate() throws Exception {
    final CLParser clParser = new CLParser(Strs.of(ClassLoader.getSystemResource("test.xml").getFile(), "oracle=oracle", "inceptor=inceptor"));
    InputStream xmlFile = clParser.getInput();
    final TestsConfig parse = new ConfigUnmarshaller().parse(new XMLParserSource(xmlFile));
    EnumMap<Dialect, Path> map = clParser.getOutputDir();
    parse.generate(OutputConfig.configureSql(map));
  }

}