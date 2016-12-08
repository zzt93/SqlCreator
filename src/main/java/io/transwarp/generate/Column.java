package io.transwarp.generate;

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
}
