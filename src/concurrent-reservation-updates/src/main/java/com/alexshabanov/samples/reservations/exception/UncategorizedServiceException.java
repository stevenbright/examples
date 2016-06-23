package com.alexshabanov.samples.reservations.exception;

public final class UncategorizedServiceException extends ServiceException {
  public UncategorizedServiceException() {
  }

  public UncategorizedServiceException(String message) {
    super(message);
  }

  public UncategorizedServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public UncategorizedServiceException(Throwable cause) {
    super(cause);
  }
}
