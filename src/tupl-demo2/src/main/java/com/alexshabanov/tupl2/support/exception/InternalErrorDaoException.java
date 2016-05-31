package com.alexshabanov.tupl2.support.exception;

/**
 * Represents internal error in DAO.
 */
public class InternalErrorDaoException extends DaoException {

  public InternalErrorDaoException() {
  }

  public InternalErrorDaoException(String message) {
    super(message);
  }

  public InternalErrorDaoException(String message, Throwable cause) {
    super(message, cause);
  }

  public InternalErrorDaoException(Throwable cause) {
    super(cause);
  }

  public InternalErrorDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
