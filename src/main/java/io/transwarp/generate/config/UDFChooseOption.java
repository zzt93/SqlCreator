package io.transwarp.generate.config;

import io.transwarp.generate.stmt.expression.Function;

import java.util.ArrayList;

/**
 * Created by zzt on 12/30/16.
 * <p>
 * <h3></h3>
 */
public class UDFChooseOption {

  private boolean moreSubQuery;
  private ArrayList<Function> prefer;
  private ArrayList<Function> deny;


  public UDFChooseOption setMoreSubQuery(boolean moreSubQuery) {
    this.moreSubQuery = moreSubQuery;
    return this;
  }

  public boolean isMoreSubQuery() {
    return moreSubQuery;
  }
}
