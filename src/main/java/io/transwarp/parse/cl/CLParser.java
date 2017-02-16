package io.transwarp.parse.cl;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.GlobalConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.EnumMap;

/**
 * Created by zzt on 2/15/17.
 * <p>
 * <h3></h3>
 */
public class CLParser {

  private String[] paths = new String[]{"oracle=oracle", "inceptor=inceptor"};
  private InputStream xmlFile = ClassLoader.getSystemResourceAsStream("template.xml");

  public CLParser(String[] args) {
    if (args.length >= 1) {
      if (!Files.exists(Paths.get(args[0]))) {
        throw new IllegalArgumentException("No such xml config file: " + xmlFile);
      }
      try {
        xmlFile = new FileInputStream(args[0]);
      } catch (FileNotFoundException impossible) {}

      if (args.length > 1) {
        paths = Arrays.copyOfRange(args, 1, args.length);
      }
    }

    if (paths.length != GlobalConfig.getCmpBase().length) {
      throw new IllegalArgumentException("Only support oracle and inceptor now");
    }
  }

  public InputStream getInputPath() {
    return xmlFile;
  }

  public EnumMap<Dialect, Path> getOutputDir() throws IOException {
    EnumMap<Dialect, Path> map = new EnumMap<>(Dialect.class);
    for (String arg : paths) {
      final String[] split = arg.split("=");
      if (split.length != 2) {
        throw new IllegalArgumentException("Invalid command line argument format: " + arg + "\nShould be 'dialect=dirName'");
      }
      final Dialect dialect = Dialect.valueOf(split[0].toUpperCase());
      final Path path = Paths.get(split[1]);
      if (!Files.exists(path)) {
        Files.createDirectory(path);
      }
      map.put(dialect, path);
    }
    return map;
  }
}
