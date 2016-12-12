package io.transwarp.generate.common;

import io.transwarp.generate.config.Config;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class TableUtil {

  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  static Column randomCol(Table table) {
    final ArrayList<Column> cols = table.columns();
    return cols.get(random.nextInt(cols.size()));
  }

  public static ArrayList<Column> randomSubTable(Table table, Config.Possibility possibility) {
    final ArrayList<Column> res = new ArrayList<>();
    for (Column column : table.columns()) {
      if (possibility.chooseFirst(true, false)) {
        res.add(column);
      }
    }
    return res;
  }
}
