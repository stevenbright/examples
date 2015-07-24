package com.alexshabanov.camelot.camel;

import com.alexshabanov.camelot.model.LogMessage;
import com.alexshabanov.camelot.model.MaterializedLogMessage;
import com.alexshabanov.camelot.model.NullLogMessage;
import com.alexshabanov.camelot.model.Severity;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexander Shabanov
 */
public final class LogMessageProcessor implements Processor {

  public static final Pattern RECORD_PATTERN = Pattern.compile(
      "^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}) " +
          "\\[([\\w\\p{Punct}]+)\\] " +
          "(\\p{Alpha}+)\\s+" +
          "([\\w\\,\\s\\.\\$\\=]+\\s)?" +
          "- ([\\w\\$\\.]+) - " +
          "(.+)$"
  );

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final DateFormat dateFormat;

  public LogMessageProcessor() {
    this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
    this.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  @Override
  public void process(Exchange exchange) throws Exception {
    final String line = exchange.getIn().getBody(String.class);
    final LogMessage logMessage = parse(line);
    exchange.getOut().setBody(logMessage);
  }

  // visible for testing
  public LogMessage parse(String line) {
    final Matcher matcher = RECORD_PATTERN.matcher(line);
    if (!matcher.matches()) {
      return NullLogMessage.INSTANCE;
    }

    if (matcher.groupCount() != 6) {
      log.error("Count of groups is not six: actual={} for line={}", matcher.groupCount(), line);
      return NullLogMessage.INSTANCE;
    }

    final Date date;
    try {
      date = dateFormat.parse(matcher.group(1));
    } catch (ParseException e) {
      return NullLogMessage.INSTANCE;
    }

    final Severity severity = Severity.fromString(matcher.group(3), Severity.WARN);
    final String message = matcher.group(6);
    final String threadId = matcher.group(2);
    final String className = matcher.group(5);

    return new MaterializedLogMessage(date.getTime(), severity, message, threadId, className, null, null);
  }
}
