package io.transwarp.generate.select;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.FromObj;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.Config;

import java.util.ArrayList;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public class SelectList implements Table {

  private final Config.Possibility possibility;
  private ArrayList<Column> cols;

  SelectList(FromObj from) {
    this(from, Config.Possibility.SELECT_POSSIBILITY);
  }

  SelectList(FromObj from, Config.Possibility possibility) {
    this.possibility = possibility;
    cols = randomCols(from);
  }

  private ArrayList<Column> randomCols(Table from) {
    final ArrayList<Column> res = new ArrayList<>();
    for (Column column : from.columns()) {
      if (possibility.chooseFirst(true, false)) {
        res.add(column);
      }
    }
    // TODO 12/9/16 add expression produced by cols
    //        res.add(Operand.randomOperand(from, 0));
    return res;
  }

  public Optional<String> name() {
    return Optional.absent();
  }

  public ArrayList<Column> columns() {
    return cols;
  }

  public StringBuilder toSql() {
    final Joiner joiner = Joiner.on(", ");
    return new StringBuilder(joiner.join(cols));
  }

  @Override
  public Column randomCol() {
    return null;
  }
}
