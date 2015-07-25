package com.alexshabanov.camelot.model;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Represents parsed log message.
 *
 * @author Alexander Shabanov
 */
public final class MaterializedLogMessage extends LogMessage {
  private final long unixTime;
  private final Severity severity;
  private final String logEntry;
  private List<String> stacktrace = null;
  private Map<String, Object> attributes = new HashMap<>();

  public MaterializedLogMessage(long unixTime, Severity severity, String logEntry) {
    this.unixTime = unixTime;
    this.severity = Objects.requireNonNull(severity, "severity");
    this.logEntry = Objects.requireNonNull(logEntry, "logEntry");
  }

  @Override
  public boolean isNull() {
    return false;
  }

  @Override
  public long getUnixTime() {
    return unixTime;
  }

  @Nonnull
  @Override
  public Severity getSeverity() {
    return severity;
  }

  @Nonnull
  @Override
  public String getLogEntry() {
    return logEntry;
  }

  @Nonnull
  @Override
  public List<String> getStackTrace() {
    return stacktrace != null ? stacktrace : Collections.<String>emptyList();
  }

  @Nonnull
  @Override
  public Map<String, Object> getAttributes() {
    return Collections.unmodifiableMap(attributes);
  }

  public void putAllAttributes(@Nonnull Map<String, ?> values) {
    this.attributes.putAll(values);
  }

  @Override
  public String toString() {
    return "MaterializedLogMessage{" +
            "unixTime=" + getUnixTime() +
            ", severity=" + getSeverity() +
            ", attributes=" + getAttributes() +
            ", logEntry='" + getLogEntry() + '\'' +
            ", stacktrace=" + getStackTrace() +
            '}';
  }
}
