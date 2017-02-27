package io.transwarp.generate.type;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public class DataTypeUtil {
  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  static List<String>[] randomSize(GenerationDataType type, int maxSize, Dialect[] dialects) {
    int times = random.nextInt(maxSize) + 1;
    List<String>[] res = new List[dialects.length];
    for (int k = 0; k < dialects.length; k++) {
      res[k] = new ArrayList<>(times);
    }
    for (int i = 0; i < times; i++) {
      final String[] strings = type.randomData(dialects);
      for (int k = 0; k < dialects.length; k++) {
        res[k].add(strings[k]);
      }
    }
    return res;
  }


  public static GenerationDataType[] extract(ArrayList<Column> columns) {
    final GenerationDataType[] res = new GenerationDataType[columns.size()];
    for (int i = 0; i < res.length; i++) {
      res[i] = columns.get(i).getType();
    }
    return res;
  }
}
