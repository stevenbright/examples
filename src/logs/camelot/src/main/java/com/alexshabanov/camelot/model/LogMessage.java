package com.alexshabanov.camelot.model;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * Represents log message.
 *
 * @author Alexander Shabanov
 */
public abstract class LogMessage {

  public abstract boolean isNull();

  @Nonnull
  public String getLogEntry() { throw new UnsupportedOperationException(); }

  @Nonnull
  public List<String> getStackTrace() { throw new UnsupportedOperationException(); }

  public long getUnixTime() {
    throw new UnsupportedOperationException();
  }

  @Nonnull
  public Severity getSeverity() {
    throw new UnsupportedOperationException();
  }

  @Nonnull
  public Map<String, Object> getAttributes() {
    throw new UnsupportedOperationException();
  }
}
