package io.transwarp.parse.sql;

import com.google.common.base.Optional;
import io.transwarp.db_specific.base.DBType;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.stmt.share.FromObj;
import io.transwarp.generate.type.GenerationDataType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 */
public class DDLParser {

  private static final String TABLE = " TABLE ";
  public static final String VIEW = " VIEW ";
  /**
   * Pattern.DOTALL or (?s) tells Java to allow the dot to match newline characters
   */
  private static Pattern columns = Pattern.compile("\\(.*\\)", Pattern.DOTALL);
  // TODO can't recognize 'a\n    NUMBER (1, 2) NOT NULL'
  private static Pattern colDetail = Pattern.compile("[^\\s]+", Pattern.DOTALL);
  private final Dialect dialect;
  private final Scanner scanner;

  DDLParser(InputStream path, Dialect dialect) throws IOException {
    scanner = new Scanner(path);
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
  public ArrayList<FromObj> parse() {
    final StmtIterator it = createSql();
    ArrayList<FromObj> tables = new ArrayList<>();
    while (it.hasNext()) {
      final String stmt = it.next();
      final Optional<String> name = extractTableName(stmt);
      if (!name.isPresent()) {
        System.out.println("[SQL Creator][Warning]: Couldn't find table/view name: " + stmt);
        continue;
      }
      final Matcher matcher = columns.matcher(stmt);
      if (matcher.find()) {
        final String group = matcher.group();
        final String[] cols = group.substring(1, group.length() - 1).split(",");
        ArrayList<Column> columns = new ArrayList<>();
        final FromObj table = new FromObj(name.get(), columns);
        for (String col : cols) {
          columns.add(extractCol(col, table));
        }
        tables.add(table);
      } else {
        throw new IllegalArgumentException("Can't find create stmt:" + stmt);
      }
    }
    return tables;
  }

  private Column extractCol(String col, Table table) {
    final Matcher m = colDetail.matcher(col);
    boolean found = m.find();
    assert found;
    String cname = m.group();
    found = m.find();
    assert found;
    String ctype = m.group();
    final String type = ctype.toUpperCase();
    final GenerationDataType mapping = dialect.getType(type).mapToGeneration(extractLen(type));
    return new Column(cname, mapping, table);
  }

  private Optional<String> extractTableName(String stmt) {
    int beginIndex = findStartOfKeyword(stmt, TABLE);
    if (beginIndex == -1) {
      beginIndex = findStartOfKeyword(stmt, VIEW);
      if (beginIndex == -1) {
        return Optional.absent();
      }
    }
    return Optional.of(stmt.substring(beginIndex, stmt.indexOf('(', beginIndex)).trim());
  }

  private int findStartOfKeyword(String stmt, String keyword) {
    int table = stmt.indexOf(keyword);
    if (table == -1) {
      table = stmt.indexOf(keyword.toLowerCase());
    }
    if (table == -1) {
      return table;
    }
    return table + keyword.length();
  }

  private int extractLen(String type) {
    try {
      return Integer.parseInt(type.replaceAll("[^\\d]*", ""));
    } catch (NumberFormatException e) {
      return DBType.NO_LEN;
    }
  }

  private static class TableLoader {

    TableLoader(String fileName, Dialect dialect) {
      DDLParser ddlParser = null;
      try {
        InputStream url = ClassLoader.getSystemResourceAsStream(fileName);
        if (url == null) {
          url = new FileInputStream(fileName);
        }
        // guess this ddl file is either in resources or in cwd
        ddlParser = new DDLParser(url, dialect);
      } catch (IOException e) {
        e.printStackTrace();
      }
      table = ddlParser.parse();
    }

    private final List<FromObj> table;
    private static HashMap<String, TableLoader> map = new HashMap<>();
  }

  public static List<Table> getTable(String tableFile, Dialect dialect) {
    List<FromObj> tables;
    if (TableLoader.map.containsKey(tableFile)) {
      tables = TableLoader.map.get(tableFile).table;
    } else {
      final TableLoader value = new TableLoader(tableFile, dialect);
      TableLoader.map.put(tableFile, value);
      tables = value.table;
    }
    return TableUtil.deepCopy(tables);
  }
}
