package io.transwarp.db_specific.base;

import io.transwarp.db_specific.OracleType;
import io.transwarp.db_specific.base.DBType;

/**
 * Created by zzt on 12/7/16.
 * <p>
 * <h3></h3>
 */
public enum Dialect {

    ORACLE(OracleType.values()),;

    private final DBType[] values;

    Dialect(DBType[] values) {
        this.values = values;
    }

    public DBType getType(String name) {
        for (DBType value : values) {
            if (name.startsWith(value.toString())) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown name:" + name);
    }
}
