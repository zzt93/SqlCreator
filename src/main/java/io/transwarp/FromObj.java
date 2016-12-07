package io.transwarp;

import com.google.common.base.Optional;

import java.util.ArrayList;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public class FromObj implements Table {

    private ArrayList<Column> columns = new ArrayList<>();
    private StringBuilder sql = new StringBuilder();


    FromObj join(FromObj table) {
        return this;
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
