package com.alexshabanov.camelot;

import com.alexshabanov.camelot.camel.LogMessageProcessor;
import com.alexshabanov.camelot.model.LogMessage;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LineParsingTest {
  private static final Pattern RECORD_PATTERN = Pattern.compile(
      "^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}) " +
          "\\[([\\w\\p{Punct}]+)\\] " +
          "(\\p{Alpha}+)\\s+" +
          "([\\w\\,\\s\\.\\$\\=]+\\s)?" +
          "- ([\\w\\$\\.]+) - " +
          "(.+)$"
  );

  private static final String MSG1 =
      "2015-07-24 05:58:38,643 [main] INFO  security=none, oid=Main.run - learn.Main - Demo info message";

  private static final String MSG2 = "2015-07-24 05:58:48,307 [main] WARN  - learn.Main - Operation timed out";

  private static final String MSG3 = "2015-07-24 06:00:26,408 [main] ERROR oid=Main.run - learn.Main - Disk full";

  private final LogMessageProcessor logMessageProcessor = new LogMessageProcessor();

  @Test
  public void shouldMatchMsg1() {
    final Matcher matcher = RECORD_PATTERN.matcher(MSG1);
    assertTrue("Matcher does not matches the message", matcher.matches());
    final LogMessage logMessage = logMessageProcessor.parse(MSG1);
    assertFalse(logMessage.isNull());
    assertValidGroups(matcher);
  }

  @Test
  public void shouldMatchMsg2() {
    final Matcher matcher = RECORD_PATTERN.matcher(MSG2);
    assertTrue("Matcher does not matches the message", matcher.matches());
    assertValidGroups(matcher);
  }

  @Test
  public void shouldMatchMsg3() {
    final Matcher matcher = RECORD_PATTERN.matcher(MSG3);
    assertTrue("Matcher does not matches the message", matcher.matches());
    assertValidGroups(matcher);
  }

  private static void assertValidGroups(Matcher matcher) {
    final String timeGroup = matcher.group(1);
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    final Date date;
    try {
      date = dateFormat.parse(timeGroup);
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }

    System.out.println("date=" + date);

    for (int i = 1; i <= matcher.groupCount(); ++i) {
      System.out.println("Group " + i + " = " + matcher.group(i));
    }
    System.out.println("  ^-- For: " + matcher.group(0) + "\n");
  }
}
