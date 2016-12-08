package io.transwarp.generate;

import com.google.common.base.Optional;

import java.util.ArrayList;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public interface Table {

    Optional<String> name();
    ArrayList<Column> columns();
    StringBuilder toSql();
}
