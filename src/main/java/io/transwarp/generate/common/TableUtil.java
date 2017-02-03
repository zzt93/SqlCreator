package io.transwarp.generate.common;

import com.google.common.base.Optional;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.op.RelationOperandConfig;
import io.transwarp.generate.type.GenerationDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

  public static Optional<Column> sameTypeRandomCol(Table[] table, GenerationDataType type) {
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

  private static ArrayList<Column> sameTypeSubCols(Table[] table, GenerationDataType type) {
    final ArrayList<Column> res = new ArrayList<>();
    for (Column column : columns(table)) {
      if (type.equals(column.getType())) {
        res.add(column);
      }
    }
    return res;
  }

  public static Table randomTable(Table[] src) {
    return src[random.nextInt(src.length)];
  }

  private static AtomicLong tableCounter = new AtomicLong(0);
  private static AtomicLong colCounter = new AtomicLong(0);

  public static String nextAlias() {
    return " alias" + tableCounter.getAndAdd(1);
  }

  static String nextColAlias() {
    return " col_alias" + colCounter.getAndAdd(1);
  }

  public static ArrayList<Column> randomSubCols(Table[] from, Possibility possibility, int colLimit) {
    final ArrayList<Column> res = new ArrayList<>();
    for (Column column : columns(from)) {
      if (possibility.chooseFirstOrRandom(true, false)) {
        res.add(column);
      }
      if (res.size() >= colLimit) {
        break;
      }
    }
    return res;
  }

  public static Table[] getTablesByName(Table[] src, List<RelationOperandConfig> operands) {
    HashMap<String, Table> map = new HashMap<>(operands.size());
    for (Table table : src) {
      assert table.name().isPresent();
      map.put(table.name().get(), table);
    }
    Table[] res = new Table[operands.size()];
    for (int i = 0; i < operands.size(); i++) {
      final RelationOperandConfig operand = operands.get(i);
      final String name = operand.getTable();
      res[i] = map.get(name);
      if (res[i] == null) {
        throw new IllegalArgumentException("Illegal table name: " + name);
      }
    }
    return res;
  }

  public static ArrayList<Column> columns(Table[] from) {
    ArrayList<Column> res = new ArrayList<>();
    for (Table table : from) {
      res.addAll(table.columns());
    }
    return res;
  }
}
