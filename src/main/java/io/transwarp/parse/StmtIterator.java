package io.transwarp.parse;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 */
public class StmtIterator implements Iterator<String> {
  private final Scanner scanner;

  public StmtIterator(Scanner scanner) {
    this.scanner = scanner;
  }

  @Override
  public boolean hasNext() {
    return scanner.hasNext();
  }

  @Override
  public String next() {
    return scanner.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
