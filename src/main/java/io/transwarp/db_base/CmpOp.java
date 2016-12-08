package io.transwarp.db_base;

/**
 * Createdbyzzton12/6/16.
 * <p>
 * <h3></h3>
 */
public enum CmpOp {

    EQ("="),
    NOT_EQ("!="),
    SMALL("<"),
    LARGE(">"),
    SMA_EQ("<="),
    LAR_EQ(">=");

    CmpOp(String s) {

    }
}
