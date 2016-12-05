package io.transwarp;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public enum DataType {
    Char {
        String getName() {
            return null;
        }
    };
    abstract String getName();
}
