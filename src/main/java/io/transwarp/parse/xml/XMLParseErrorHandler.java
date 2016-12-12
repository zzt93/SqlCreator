package io.transwarp.parse.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.PrintWriter;

/**
 * Created by zzt on 12/12/16.
 * <p>
 * <h3></h3>
 */
public class XMLParseErrorHandler implements ErrorHandler {

  private PrintWriter out;

  XMLParseErrorHandler(PrintWriter out) {
    this.out = out;
  }

  private String getParseExceptionInfo(SAXParseException spe) {
    String systemId = spe.getSystemId();
    if (systemId == null) {
      systemId = "null";
    }

    return "URI=" + systemId + " Line=" + spe.getLineNumber() +
            ": " + spe.getMessage();
  }

  public void warning(SAXParseException spe) throws ValidationException {
    out.println("Warning: " + getParseExceptionInfo(spe));
  }

  public void error(SAXParseException spe) throws ValidationException {
    String message = "Error: " + getParseExceptionInfo(spe);
    throw new ValidationException(message);
  }

  public void fatalError(SAXParseException spe) throws ValidationException {
    String message = "Fatal Error: " + getParseExceptionInfo(spe);
    throw new ValidationException(message);
  }
}
