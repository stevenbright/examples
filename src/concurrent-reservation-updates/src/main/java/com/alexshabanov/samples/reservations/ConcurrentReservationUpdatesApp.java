package com.alexshabanov.samples.reservations;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point.
 */
public final class ConcurrentReservationUpdatesApp {

  public static void main(String[] args) {
    try (final AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:/spring/app-context.xml")) {
      doMain(applicationContext);
      LoggerFactory.getLogger(ConcurrentReservationUpdatesApp.class).info("DONE.");
    }
  }

  private static void doMain(ApplicationContext context) {
    final Runnable appRunner = context.getBean(Runnable.class);
    appRunner.run();
  }
}
