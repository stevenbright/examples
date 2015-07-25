package learn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public final class LogProducerMain implements Runnable {
  private final Logger log;
  private final String[] args;
  private final Random random = new Random(System.currentTimeMillis());

  private static final String LOG_DEBUG = "logDebug";
  private static final String LOG_INFO = "logInfo";
  private static final String LOG_WARN = "logWarn";
  private static final String LOG_ERROR = "logError";
  private static final String LOG_EXCEPTION = "logException";

  private LogProducerMain(String[] args) {
    this.args = args;
    this.log = LoggerFactory.getLogger(getClass());
  }

  public static void main(String[] args) throws Exception {
    System.setProperty("logback.configurationFile", "default-logback-config.xml");
    new LogProducerMain(args).run();
  }

  private String getRandomBase64() {
    final byte[] bytes = new byte[12];
    random.nextBytes(bytes);
    return Base64.getEncoder().encodeToString(bytes);
  }

  @Override
  public void run() {
    MDC.put("oid", getRandomBase64());
    MDC.put("rid", getRandomBase64());

    log.info("Application started with args={}", Arrays.toString(args));

    try (final BufferedReader r = new BufferedReader(new InputStreamReader(System.in))) {
      repl(r);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    log.info("Stopping application");
  }

  //
  // Private
  //

  private void printHelp() {
    System.out.println("Run one of the following: quit, help, clearMdc, addRidMdc, addOidMdc, " +
            LOG_EXCEPTION + ", " +
            LOG_WARN + " <optional message>, " +
            LOG_INFO + " <optional message>, " +
            LOG_ERROR + " <optional message>");
    System.out.println();
  }

  private void repl(BufferedReader r) throws IOException {
    for (int num = 0;; ++num) {
      System.out.print("> ");
      final String line = r.readLine();
      if (line == null) {
        break; // Ctrl-C
      }
      if ("quit".equals(line)) { return; }

      if (line.equals("clearMdc")) {
        MDC.clear();
        System.out.println("All MDC entries removed");
        continue;
      }

      if (line.equals("addRidMdc")) {
        MDC.put("rid", getRandomBase64());
        System.out.println("rid MDC has been added");
        continue;
      }

      if (line.equals("addOidMdc")) {
        MDC.put("oid", getRandomBase64());
        System.out.println("oid MDC has been added");
        continue;
      }

      if (line.startsWith(LOG_DEBUG)) {
        String content = line.substring(LOG_DEBUG.length()).trim();
        content = content.isEmpty() ? "Demo debug message" : content;
        log.debug(content);
        continue;
      }

      if (line.startsWith(LOG_WARN)) {
        String content = line.substring(LOG_WARN.length()).trim();
        content = content.isEmpty() ? "Demo warn message" : content;
        log.warn(content);
        continue;
      }

      if (line.startsWith(LOG_INFO)) {
        String content = line.substring(LOG_INFO.length()).trim();
        content = content.isEmpty() ? "Demo info message" : content;
        log.info(content);
        continue;
      }

      if (line.startsWith(LOG_ERROR)) {
        String content = line.substring(LOG_ERROR.length()).trim();
        content = content.isEmpty() ? "Demo error message" : content;
        log.error(content);
        continue;
      }

      if (line.equals(LOG_EXCEPTION)) {
        try {
          doErrorOperation();
        } catch (UnsupportedOperationException e) {
          log.error("Error operation #{} attempted", num, e);
        }
        continue;
      }

      if (line.startsWith("help")) {
        printHelp();
        continue;
      }

      System.err.println("Unrecognized command: " + line);
      printHelp();
    }
  }

  private static void doErrorOperation() {
    throwUnsupportedOperationException();
  }

  private static void throwUnsupportedOperationException() {
    throw new UnsupportedOperationException("This operation is not supported yet");
  }
}
