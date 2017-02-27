package io.transwarp.parse;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class ParseException extends Exception {
  private final Exception exception;

  public ParseException(Exception e) {
    this.exception = e;
  }

  @Override
  public String toString() {
    return "ParseException{" +
            "exception=" + exception.getMessage() +
            '}';
  }
}
