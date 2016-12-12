package io.transwarp.parse;

import io.transwarp.generate.config.*;

import java.io.IOException;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public interface ConfigParser {

  Config parse(ParserSource source) throws ParseException, IOException;
}
