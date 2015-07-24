package com.alexshabanov.camelot.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.Objects;

/**
 * Represents parsed log message.
 *
 * @author Alexander Shabanov
 */
public final class MaterializedLogMessage extends LogMessage {
  private final long unixTime;
  private final Severity severity;
  private final String message;
  private final String threadId;
  private final String className;
  private final String requestId;
  private final String originatingRequestId;

  public MaterializedLogMessage(long unixTime, Severity severity, String message, String threadId, String className,
                                String requestId, String originatingRequestId) {
    this.unixTime = unixTime;
    this.severity = Objects.requireNonNull(severity, "severity");
    this.message = Objects.requireNonNull(message, "message");
    this.threadId = Objects.requireNonNull(threadId, "threadId");
    this.className = Objects.requireNonNull(className, "className");
    this.requestId = requestId;
    this.originatingRequestId = originatingRequestId;
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
  public String getMessage() {
    return message;
  }

  @Nonnull
  @Override
  public String getThreadId() {
    return threadId;
  }

  @Nonnull
  @Override
  public String getClassName() {
    return className;
  }

  @Nullable
  @Override
  public String getRequestId() {
    return requestId;
  }

  @Nullable
  @Override
  public String getOriginatingRequestId() {
    return originatingRequestId;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder(200 + getMessage().length());
    builder.append('{')
        .append("severity: '").append(getSeverity()).append('\'')
        .append(", unixTime: '").append(new Date(getUnixTime())).append('\'')
        .append(", className: '").append(getClassName()).append('\'')
        .append(", threadId: '").append(getThreadId()).append('\'');



    if (getRequestId() != null) {
      builder.append(", requestId: \'").append(getRequestId()).append('\'');
    }

    if (getOriginatingRequestId() != null) {
      builder.append(", originatingRequestId: '").append(getOriginatingRequestId()).append('\'');
    }

    builder.append(", message: '").append(getMessage()).append('\'');
    builder.append('}');

    return builder.toString();
  }
}
