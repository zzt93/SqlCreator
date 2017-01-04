package io.transwarp.generate.common;

import com.google.common.base.Optional;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.type.GenerationDataType;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class TableUtil {

  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  public static Column randomCol(Table table) {
    final ArrayList<Column> cols = table.columns();
    return cols.get(random.nextInt(cols.size()));
  }

  public static Optional<Column> sameTypeRandomCol(Table table, GenerationDataType type) {
    final ArrayList<Column> cols = sameTypeSubCols(table, type);
    if (cols.isEmpty()) {
      return Optional.absent();
    }
    return Optional.of(cols.get(random.nextInt(cols.size())));
  }

  public static ArrayList<Column> randomSubCols(Table table, int size) {
    final ArrayList<Column> res = new ArrayList<>(size);
    while (res.size() < size) {
      res.add(randomCol(table));
    }
    return res;
  }

  public static ArrayList<Column> sameTypeSubCols(Table table, GenerationDataType type) {
    final ArrayList<Column> res = new ArrayList<>();
    for (Column column : table.columns()) {
      if (type.equals(column.getType())) {
        res.add(column);
      }
    }
    return res;
  }

  public static Table randomTable(Table[] src) {
    return src[random.nextInt(src.length)];
  }

  private static AtomicLong atomicLong = new AtomicLong(0);

  public static String nextAlias() {
    return "alias" + atomicLong.getAndAdd(1);
  }

  public static ArrayList<Column> randomSubCols(Table from, Possibility possibility, int colLimit) {
    final ArrayList<Column> res = new ArrayList<>();
    for (Column column : from.columns()) {
      if (possibility.chooseFirstOrRandom(true, false)) {
        res.add(column);
      }
      if (res.size() >= colLimit) {
        break;
      }
    }
    return res;
  }
}
