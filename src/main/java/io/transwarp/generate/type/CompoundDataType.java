package io.transwarp.generate.type;

import com.google.common.base.Strings;

/**
 * Created by zzt on 12/7/16.
 * <p>
 * <h3></h3>
 */
public class CompoundDataType implements GenerationDataType {

    private final DataType type;
    private final int len;

    public CompoundDataType(DataType type, int len) {
        this.type = type;
        this.len = len;
    }

    @Override
    public String getRandom() {
        return null;
    }

    @Override
    public String getMax() {
        return Strings.repeat(type.getMax(), len);
    }

    @Override
    public String getMin() {
        return type.getMin();
    }
}
