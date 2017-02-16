package io.transwarp.parse.cl;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.util.Strs;
import io.transwarp.parse.xml.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.EnumMap;

/**
 * Created by zzt on 2/16/17.
 * <p>
 * <h3></h3>
 */
public class CLParserTest {

  private final CLParser clParser;

  public CLParserTest() throws IOException, ValidationException {
    clParser = new CLParser(Strs.of(ClassLoader.getSystemResource("test.xml").getFile(), "oracle=oracle", "inceptor=inceptor"));
  }

  @Test
  public void noSuchFile() throws Exception {
    boolean error = false;
    try {
      CLParser clParser = new CLParser(Strs.of("test.xml", "oracle=oracle", "inceptor=inceptor"));
    } catch (Exception e) {
      if (e instanceof IllegalArgumentException) {
        error = true;
      }
    }
    Assert.assertTrue(error);
  }


  @Test
  public void getOutputDir() throws Exception {
    final EnumMap<Dialect, Path> outputDir = clParser.getOutputDir();
    System.out.println(outputDir);
  }

}