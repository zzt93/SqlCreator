package io.transwarp.generate.condition;

import com.google.common.base.Optional;
import io.transwarp.generate.common.Column;

/**
 * Created by zzt on 12/8/16.
 * <p>
 * <h3></h3>
 */
public class CmpOperand {

    private String nameOrConst;

    public CmpOperand(Column column) {
        nameOrConst = column.getNameOrConst();
    }

    @Override
    public String toString() {
        return nameOrConst;
    }
}
