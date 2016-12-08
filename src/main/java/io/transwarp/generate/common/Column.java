package io.transwarp.generate.common;

import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 *
 * @see GenerationDataType
 */
public class Column {

    /**
     * use @see DataTypeGeneration for this type is for generation
     * and should isolate from the specific sql dialect
     */
    private GenerationDataType type;
    private String name;

    private Table table;


    public Column(String name, GenerationDataType type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public String toString() {
        return table.name() + "." + name;
    }

    public String getNameOrConst() {
        return type.getRandom();
    }
}
