package io.transwarp.generate.condition;

/**
 * Created by zzt on 12/5/16.
 * <p>
 * <h3></h3>
 */
public enum LogicalOp {
    AND(" AND "), OR(" OR "), NOT(" NOT ");

    private final StringBuilder name;

    LogicalOp(String s) {
        name = new StringBuilder(s);
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
