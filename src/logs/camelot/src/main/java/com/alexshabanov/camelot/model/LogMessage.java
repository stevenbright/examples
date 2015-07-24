package com.alexshabanov.camelot.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents log message.
 *
 * @author Alexander Shabanov
 */
public abstract class LogMessage {

  public abstract boolean isNull();

  public long getUnixTime() {
    throw new UnsupportedOperationException();
  }

  @Nonnull
  public Severity getSeverity() {
    throw new UnsupportedOperationException();
  }

  @Nonnull
  public String getMessage() {
    throw new UnsupportedOperationException();
  }

  @Nonnull
  public String getThreadId() {
    throw new UnsupportedOperationException();
  }

  @Nonnull
  public String getClassName() {
    throw new UnsupportedOperationException();
  }

  @Nullable
  public String getRequestId() {
    throw new UnsupportedOperationException();
  }

  @Nullable
  public String getOriginatingRequestId() {
    throw new UnsupportedOperationException();
  }
}
