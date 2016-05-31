package com.alexshabanov.tupl2.support.exception;

/**
 * Exception thrown when particular object can't be found.
 */
public class ResultNotFoundException extends DaoException {
  public ResultNotFoundException() {
  }

  public ResultNotFoundException(String message) {
    super(message);
  }

  public ResultNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResultNotFoundException(Throwable cause) {
    super(cause);
  }

  public ResultNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
