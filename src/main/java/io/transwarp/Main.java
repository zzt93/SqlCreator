package io.transwarp;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.parse.cl.CLParser;
import io.transwarp.parse.xml.ConfigUnmarshaller;
import io.transwarp.parse.xml.ValidationException;
import io.transwarp.parse.xml.XMLParserSource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.EnumMap;

/**
 * Created by zzt on 2/15/17.
 * <p>
 * <h3></h3>
 */
public class Main {

  public static void main(String[] args) throws IOException, ValidationException {
    final CLParser clParser = new CLParser(args);
    String xmlFile = clParser.getInputPath();
    final GlobalConfig parse = new ConfigUnmarshaller().parse(new XMLParserSource(xmlFile));
    EnumMap<Dialect, Path> map = clParser.getOutputDir();
    parse.generate(map);
  }

}
