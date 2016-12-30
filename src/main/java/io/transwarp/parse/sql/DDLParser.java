package io.transwarp.parse.sql;

import com.google.common.base.Optional;
import io.transwarp.db_specific.base.DBType;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.stmt.share.FromObj;
import io.transwarp.generate.type.GenerationDataType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 */
public class DDLParser {

  private static final String TABLE = "TABLE";
  /**
   * Pattern.DOTALL or (?s) tells Java to allow the dot to match newline characters
   */
  private static Pattern columns = Pattern.compile("\\(.*\\)", Pattern.DOTALL);
  // TODO can't recognize 'a\n    NUMBER (1, 2) NOT NULL'
  private static Pattern colDetail = Pattern.compile("[^\\s]+", Pattern.DOTALL);
  private final Dialect dialect;
  private final Scanner scanner;

  public DDLParser(String path, Dialect dialect) throws IOException {
    Path sqlFile = Paths.get(path);
    if (!Files.exists(sqlFile)) {
      throw new IllegalArgumentException("File " + path + " not exists");
    }
    scanner = new Scanner(sqlFile);
    scanner.useDelimiter(";");
    this.dialect = dialect;
  }

  private StmtIterator createSql() {
    return new StmtIterator(scanner);
  }

  /**
   * <a href="https://docs.oracle.com/cd/B14156_01/doc/B13812/html/sqcmd.htm#BABFBEFF">create table syntax</a>
   *
   * @return the object defined by ddl
   */
  public Table[] parse() {
    final StmtIterator it = createSql();
    ArrayList<Table> tables = new ArrayList<>();
    while (it.hasNext()) {
      final String stmt = it.next();
      final Optional<String> name = extractTableName(stmt);
      if (!name.isPresent()) {
        continue;
      }
      final Matcher matcher = columns.matcher(stmt);
      if (matcher.find()) {
        final String group = matcher.group();
        final String[] cols = group.substring(1, group.length() - 1).split(",");
        ArrayList<Column> columns = new ArrayList<>();
        final Table table = new FromObj(name.get(), columns);
        for (String col : cols) {
          columns.add(extractCol(col, table));
        }
        // TODO how to require join column
        tables.add(table);
      } else {
        throw new IllegalArgumentException("Can't find create stmt:" + stmt);
      }
    }
    return tables.toArray(new Table[]{});
  }

  private Column extractCol(String col, Table table) {
    final Matcher m = colDetail.matcher(col);
    assert m.find();
    String cname = m.group();
    assert m.find();
    String ctype = m.group();
    final String type = ctype.toUpperCase();
    final GenerationDataType mapping = dialect.getType(type).mapToGeneration(extractLen(type));
    return new Column(cname, mapping, table);
  }

  private Optional<String> extractTableName(String stmt) {
    int table = stmt.indexOf(TABLE);
    if (table == -1) {
      table = stmt.indexOf(TABLE.toLowerCase());
    }
    if (table == -1) {
      return Optional.absent();
    }
    final int beginIndex = table + TABLE.length();
    return Optional.of(stmt.substring(beginIndex, stmt.indexOf('(', beginIndex)).trim());
  }

  private int extractLen(String type) {
    try {
      return Integer.parseInt(type.replaceAll("[^\\d]*", ""));
    } catch (NumberFormatException e) {
      return DBType.NO_LEN;
    }
  }

  private static class TableLoader {
    static {
      DDLParser ddlParser = null;
      try {
        ddlParser = new DDLParser("src/main/resources/default_oracle.sql", Dialect.ORACLE);
      } catch (IOException e) {
        e.printStackTrace();
      }
      table = ddlParser.parse();
    }

    private static final Table[] table;
  }

  public static Table[] getTable() {
    return TableLoader.table;
  }
}
