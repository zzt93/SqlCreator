package io.transwarp;

import com.google.inject.AbstractModule;
import io.transwarp.out.SqlWriter;
import io.transwarp.out.file.FileSqlWriter;
import io.transwarp.parse.ConfigParser;
import io.transwarp.parse.ParserSource;
import io.transwarp.parse.xml.ConfigUnmarshaller;
import io.transwarp.parse.xml.XMLParserSource;

/**
 * Created by zzt on 17/4/27.
 *
 * bind service-like class
 */
public class SqlGeneratorModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(ParserSource.class).to(XMLParserSource.class);
    bind(ConfigParser.class).to(ConfigUnmarshaller.class);
  }
}
