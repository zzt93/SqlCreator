package io.transwarp.generate.condition;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 */
public enum CmpOp {

    EQ("="),
    NOT_EQ("!="),
    SMALL("<"),
    LARGE(">"),
    SMA_EQ("<="),
    LAR_EQ(">="),
    IS_NULL("IS NULL"),
    LIKE("LIKE"),
    BETWEEN("")
    ;

    CmpOp(String s) {

    }
}