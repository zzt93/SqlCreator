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
    private StringBuilder stringBuilder;

    public CmpCondition(Table from) {
        CmpOp op = CmpOp.randomOp();
        stringBuilder = new StringBuilder(op.toSql(from));
    }

    public StringBuilder toSql() {
        return stringBuilder;
    }

}
