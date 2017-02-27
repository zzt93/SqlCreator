package io.transwarp.parse.xml;

import org.xml.sax.SAXException;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class ValidationException extends SAXException {

  ValidationException(Exception e) {
    super(e);
  }

  public ValidationException(String message) {
    super(message);
  }
}
