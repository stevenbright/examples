package learn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public final class Main implements Runnable {
  private final Logger log;
  private final String[] args;

  private static final String LOG_DEBUG = "logDebug";
  private static final String LOG_INFO = "logInfo";
  private static final String LOG_WARN = "logWarn";
  private static final String LOG_ERROR = "logError";

  private Main(String[] args) {
    this.args = args;
    this.log = LoggerFactory.getLogger(getClass());
  }

  public static void main(String[] args) throws Exception {
    System.setProperty("logback.configurationFile", "default-logback-config.xml");
    new Main(args).run();
  }

  @Override
  public void run() {
    MDC.put("oid", "Main.run");
    MDC.put("security", "none");
    log.info("Application started with args={}", Arrays.toString(args));

    try (final BufferedReader r = new BufferedReader(new InputStreamReader(System.in))) {
      repl(r);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    MDC.remove("security");
    log.info("Stopping application");
  }

  //
  // Private
  //

  private void printHelp() {
    System.out.println("Run one of the following: quit, help, " + LOG_WARN + " <optional message>, " +
            LOG_INFO + " <optional message>, " + LOG_ERROR + " <optional message>");
    System.out.println();
  }

  private void repl(BufferedReader r) throws IOException {
    for (;;) {
      System.out.print("> ");
      final String line = r.readLine();
      if (line == null) {
        break; // Ctrl-C
      }
      if ("quit".equals(line)) { return; }

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

      if (line.startsWith("help")) {
        printHelp();
        continue;
      }

      System.err.println("Unrecognized command: " + line);
      printHelp();
    }
  }
}
