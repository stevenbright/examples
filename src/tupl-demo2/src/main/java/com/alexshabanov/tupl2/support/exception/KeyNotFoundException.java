package com.alexshabanov.tupl2.support.exception;

/**
 * An error, thrown when given key has not been found.
 */
public class KeyNotFoundException extends DaoException {

  public KeyNotFoundException() {
  }

  public KeyNotFoundException(String message) {
    super(message);
  }

  public KeyNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public KeyNotFoundException(Throwable cause) {
    super(cause);
  }
}
