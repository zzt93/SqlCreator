package io.transwarp.parse;

import io.transwarp.FromObj;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 */
public class DDLParser {

    private static Pattern columns = Pattern.compile("\\([^\\)]+\\)");
    private Scanner scanner;

    public DDLParser(String path) throws IOException {
        Path sqlFile = Paths.get(path);
        if (!Files.exists(sqlFile)) {
            throw new IllegalArgumentException("File " + path + " not exists");
        }
        scanner = new Scanner(sqlFile);
        scanner.useDelimiter(";");
    }

    private StmtIterator createSql() {
        return new StmtIterator(scanner);
    }

    public FromObj parse() {
        final StmtIterator it = createSql();
        while (it.hasNext()) {
            final String stmt = it.next();
            final Matcher matcher = columns.matcher(stmt);
            if (matcher.find()) {

            }
        }
        return null;
    }
}
