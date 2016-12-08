package io.transwarp.generate.type;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 */
public enum DataTypeGroup {

    BoolGroup(DataType.BOOL),
    NumGroup(DataType.BYTE, DataType.SHORT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE, DataType.DECIMAL),
    StringGroup(DataType.CHAR),
    DateGroup(DataType.DATE, DataType.TIME, DataType.TIMESTAMP);

    private List<DataType> types;
    DataTypeGroup(DataType... types) {
        this.types = Arrays.asList(types);
    }

    public static DataTypeGroup sameGroup(DataType type) {
        for (DataTypeGroup dataTypeGroup : values()) {
            if (dataTypeGroup.types.contains(type)) {
                return dataTypeGroup;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

    public String random() {
        return null;
    }
}
