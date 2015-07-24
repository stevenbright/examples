package com.alexshabanov.camelot.camel;

import com.alexshabanov.camelot.model.LogMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

/**
 * Filter, that throws away malformed log messages.
 *
 * @author Alexander Shabanov
 */
public final class MalformedLogMessageFilter implements Predicate {

  @Override
  public boolean matches(Exchange exchange) {
    final LogMessage logMessage = exchange.getIn().getBody(LogMessage.class);
    return (logMessage != null) && (!logMessage.isNull());
  }
}
