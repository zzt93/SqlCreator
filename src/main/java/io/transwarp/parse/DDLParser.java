package io.transwarp.parse;

import io.transwarp.db_base.Dialect;
import io.transwarp.generate.Column;
import io.transwarp.generate.FromObj;
import io.transwarp.generate.GenerationDataType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static Pattern columns = Pattern.compile("\\([^\\)]+\\)");
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
     * <a href="https://docs.oracle.com/cd/B14156_01/doc/B13812/html/sqcmd.htm#BABFBEFF">create table bnf</a>
     *
     * @return the object defined by ddl
     */
    public FromObj parse() {
        final StmtIterator it = createSql();
        while (it.hasNext()) {
            final String stmt = it.next();
            int table = stmt.indexOf(TABLE);
            if (table == -1) {
                table = stmt.indexOf(TABLE.toLowerCase());
            }
            final String name = stmt.substring(table + TABLE.length(), stmt.indexOf('(', table));
            final Matcher matcher = columns.matcher(stmt);
            if (matcher.find()) {
                final String[] cols = matcher.group().split(",");
                System.out.printf(Arrays.toString(cols));
                ArrayList<Column> columns = new ArrayList<>();
                for (String col : cols) {
                    final String[] colDetail = col.split("\\s+|\\(");
                    if (colDetail.length == 0) {
                        continue;
                    }
                    // TODO always right?
                    final String type = colDetail[1].toUpperCase();
                    final GenerationDataType mapping = dialect.getType(type).mapping(extractLen(type));
                    columns.add(new Column(colDetail[0], mapping));
                }
                // TODO how to require join column
                return new FromObj(name, columns);
            }
        }
        return null;
    }

    private int extractLen(String type) {
        try {
            return Integer.parseInt(type);
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
