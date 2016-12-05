package io.transwarp;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public class WhereCondition {
    private StringBuilder stringBuilder;

    public WhereCondition and(WhereCondition condition) {
        return this;
    }

    public WhereCondition or(WhereCondition condition) {
        return this;
    }


    public WhereCondition not() {
        return this;
    }

    public StringBuilder toSql() {
        return stringBuilder;
    }

}
