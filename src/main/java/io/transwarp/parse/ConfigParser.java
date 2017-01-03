package io.transwarp.parse;

import io.transwarp.generate.config.GlobalConfig;

import java.io.IOException;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public interface ConfigParser {

  GlobalConfig parse(ParserSource source) throws ParseException, IOException;
}
