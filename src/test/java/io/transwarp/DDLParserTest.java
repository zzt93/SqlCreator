package io.transwarp;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.parse.DDLParser;
import org.junit.Test;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 */
public class DDLParserTest {
    @Test
    public void delimiterTest() throws Exception {
        final DDLParser ddlParser = new DDLParser("src/test/src.sql", Dialect.ORACLE);
        ddlParser.parse();
    }

}