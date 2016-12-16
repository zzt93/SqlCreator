package io.transwarp.generate.type;

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

  static List<String> randoms(GenerationDataType type, int max) {
    int times = random.nextInt(max);
    List<String> res = new ArrayList<>(times);
    for (int i = 0; i < times; i++) {
      res.add(type.randomData());
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
