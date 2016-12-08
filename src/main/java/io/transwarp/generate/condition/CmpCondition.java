package io.transwarp.generate.condition;

import io.transwarp.generate.common.Table;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 * <br>states</br>
 * <br>operation</br>
 * <br>result</br>
 */
public class CmpCondition extends Condition {
    private StringBuilder stringBuilder = new StringBuilder();
    private Table limit;

    public CmpCondition(Table limit) {
        this.limit = limit;
    }

    @Override
    void generate() {

    }

    public StringBuilder toSql() {
        return stringBuilder;
    }

}
