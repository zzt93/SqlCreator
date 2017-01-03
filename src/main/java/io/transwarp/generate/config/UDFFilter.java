package io.transwarp.generate.config;

import io.transwarp.generate.stmt.expression.CmpOp;
import io.transwarp.generate.stmt.expression.Function;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzt on 12/30/16.
 * <p>
 * <h3></h3>
 */
public class UDFFilter {

  private boolean moreSubQuery;
  /**
   * used to implement:
   * <li>differences between select list generation and where condition generation: aggregate function</li>
   * <li>specific function requirement: non-equal join; no sub-query</li>
   */
  private Map<Function, Possibility> preference = new HashMap<>();

  public UDFFilter addPreference(Function f, Possibility p) {
    preference.put(f, p);
    return this;
  }

  public UDFFilter removePreference(Function f) {
    preference.remove(f);
    return this;
  }

  public UDFFilter subQuery(boolean moreSubQuery) {
    if (moreSubQuery != this.moreSubQuery) {
      if (!moreSubQuery) {
        addPreference(CmpOp.IN, Possibility.DENY)
            .addPreference(CmpOp.NOT_IN, Possibility.DENY)
            .addPreference(CmpOp.EXISTS, Possibility.DENY);
      } else {
        removePreference(CmpOp.IN).removePreference(CmpOp.NOT_IN).removePreference(CmpOp.EXISTS);
      }
      this.moreSubQuery = moreSubQuery;
    }
    return this;
  }

  public boolean want(Function function) {
    Possibility possibility = preference.get(function);
    if (possibility == null) {
      possibility = Possibility.NORMAL;
    }
    return possibility.chooseFirstOrRandom(true, false);
  }
}
