package io.transwarp.parse;

import io.transwarp.generate.config.PerGenerationConfig;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public interface ConfigParser {

  void parseGlobal() throws ParseException;
  Iterator<PerGenerationConfig> parseEach() throws ParseException;

  interface ConfigParserBuilder {
    ConfigParser build(ParserSource parserSource) throws IOException;
  }
}
