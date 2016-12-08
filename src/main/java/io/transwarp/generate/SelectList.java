package io.transwarp.generate;

import com.google.common.base.Optional;

import java.util.ArrayList;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public class SelectList implements Table {

    private final Table limit;

    public SelectList(Table limit) {
        this.limit = limit;
    }

    public Optional<String> name() {
        return null;
    }

    public ArrayList<Column> columns() {
        return null;
    }

    public StringBuilder toSql() {
        return null;
    }
}
