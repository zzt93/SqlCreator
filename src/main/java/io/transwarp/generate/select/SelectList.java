package io.transwarp.generate.select;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.Table;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public class SelectList implements Table {

    private final double possibility;
    private ArrayList<Column> cols;

    public SelectList(Table from) {
        this(from, 0.5);
    }

    public SelectList(Table from, double possibility) {
        this.possibility = possibility;
        cols = randomCols(from);
    }

    private Random random = new Random(12);

    private ArrayList<Column> randomCols(Table from) {
        final ArrayList<Column> res = new ArrayList<>();
        for (Column column : from.columns()) {
            if (random.nextDouble() < possibility) {
                res.add(column);
            }
        }
        return res;
    }

    public Optional<String> name() {
        return Optional.absent();
    }

    public ArrayList<Column> columns() {
        return cols;
    }

    public StringBuilder toSql() {
        final Joiner joiner = Joiner.on(", ");
        return new StringBuilder(joiner.join(cols));
    }
}
