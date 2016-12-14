package io.transwarp.generate.type;

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
    for (int i = 0; i < res.size(); i++) {
      res.add(type.getRandom());
    }
    return res;
  }
}
