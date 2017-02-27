package io.transwarp.generate.common;

import com.google.common.base.Optional;
import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.stmt.share.FromObj;
import io.transwarp.generate.type.GenerationDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class TableUtil {

  public static final String INVALID_ALIAS = "";
  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  public static Column randomCol(Table table) {
    final ArrayList<Column> cols = table.columns();
    return cols.get(random.nextInt(cols.size()));
  }

  public static Optional<Column> sameTypeRandomCol(List<Table> tables, GenerationDataType type) {
    final ArrayList<Column> cols = sameTypeSubCols(tables, type);
    if (cols.isEmpty()) {
      return Optional.absent();
    }
    return Optional.of(cols.get(random.nextInt(cols.size())));
  }

  private static ArrayList<Column> sameTypeSubCols(List<Table> tables, GenerationDataType type) {
    final ArrayList<Column> res = new ArrayList<>();
    for (Column column : columns(tables)) {
      if (type.equals(column.getType())) {
        res.add(column);
      }
    }
    return res;
  }

  public static Table randomTable(List<Table> src) {
    return src.get(random.nextInt(src.size()));
  }

  private static AtomicLong tableCounter = new AtomicLong(0);
  private static AtomicLong colCounter = new AtomicLong(0);

  public static String nextAlias() {
    return "alias" + tableCounter.getAndAdd(1);
  }

  public static String nextColAlias() {
    return "col_alias" + colCounter.getAndAdd(1);
  }

  public static ArrayList<Column> randomSubCols(List<Table> from, BiChoicePossibility biChoicePossibility, int colLimit) {
    final ArrayList<Column> res = new ArrayList<>();
    for (Column column : columns(from)) {
      if (biChoicePossibility.random(true, false)) {
        res.add(column);
      }
      if (res.size() >= colLimit) {
        break;
      }
    }
    return res;
  }

  public static Table getTableByName(List<Table> src, String tableName) {
    for (Table table : src) {
      assert table.name().isPresent();
      if (table.name().get().equals(tableName)) {
        return table;
      }
    }
    throw new IllegalArgumentException("Illegal table name: " + tableName);
  }

  public static ArrayList<Column> columns(List<Table> tables) {
    ArrayList<Column> res = new ArrayList<>();
    for (Table table : tables) {
      res.addAll(table.columns());
    }
    return res;
  }

  public static List<Table> deepCopy(List<FromObj> tables) {
    final List<Table> res = new ArrayList<>(tables.size());
    for (FromObj table : tables) {
      res.add(new FromObj(table));
    }
    return res;
  }

  public static Table deepCopy(Table table) {
    return new FromObj(table);
  }

  public static boolean invalidAlias(String name) {
    return name == null || name.equals(INVALID_ALIAS);
  }
}
