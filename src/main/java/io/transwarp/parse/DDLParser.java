package io.transwarp.parse;

import io.transwarp.db_specific.base.DBType;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.FromObj;
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
    public FromObj parse() {
        final StmtIterator it = createSql();
        while (it.hasNext()) {
            final String stmt = it.next();
            final String name = extractTableName(stmt);
            final Matcher matcher = columns.matcher(stmt);
            if (matcher.find()) {
                final String group = matcher.group();
                final String[] cols = group.substring(1, group.length() - 1).split(",");
                ArrayList<Column> columns = new ArrayList<>();
                for (String col : cols) {
                    columns.add(extractCol(col));
                }
                // TODO how to require join column
                return new FromObj(name, columns);
            } else {
                throw new IllegalArgumentException("Can't find create stmt:" + stmt);
            }
        }
        return null;
    }

    private Column extractCol(String col) {
        final Matcher m = colDetail.matcher(col);
        assert m.find();
        String cname = m.group();
        assert m.find();
        String ctype = m.group();
        final String type = ctype.toUpperCase();
        final GenerationDataType mapping = dialect.getType(type).mapToGeneration(extractLen(type));
        return new Column(cname, mapping);
    }

    private String extractTableName(String stmt) {
        int table = stmt.indexOf(TABLE);
        if (table == -1) {
            table = stmt.indexOf(TABLE.toLowerCase());
        }
        return stmt.substring(table + TABLE.length(), stmt.indexOf('(', table));
    }

    private int extractLen(String type) {
        try {
            return Integer.parseInt(type.replaceAll("[^\\d]*", ""));
        } catch (NumberFormatException e) {
            return DBType.NO_LEN;
        }
    }
}
