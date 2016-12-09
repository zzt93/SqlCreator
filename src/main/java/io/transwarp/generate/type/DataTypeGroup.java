package io.transwarp.generate.type;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 */
public enum DataTypeGroup {

    BoolGroup(DataType.BOOL),
    NumGroup(new CompoundDataType(DataType.BIT, 0), DataType.BYTE, DataType.SHORT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE, DataType.DECIMAL),
    StringGroup(new CompoundDataType(DataType.CHAR, 0), new CompoundDataType(DataType.UNICODE, 0)),
    DateGroup(DataType.DATE, DataType.TIME, DataType.TIMESTAMP);

    private List<GenerationDataType> types;

    DataTypeGroup(GenerationDataType... types) {
        this.types = Arrays.asList(types);
    }

    public static DataTypeGroup sameGroup(GenerationDataType type) {
        for (DataTypeGroup dataTypeGroup : values()) {
            if (dataTypeGroup.contains(type)) {
                return dataTypeGroup;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

    private Random random = new Random(12);
    private static int groups = DataTypeGroup.values().length;
    public String random() {
        types.get(random.nextInt(groups));
        return null;
    }

    public boolean contains(GenerationDataType type) {
        return types.contains(type);
    }
}
