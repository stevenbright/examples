package com.alexshabanov.sample.cassandra.hello;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloCassandraApp {

  public static void main(String[] args) {
    System.out.println("In tx concurrency test app!");

    try (AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:/spring/app-context.xml")) {
      doMain(applicationContext);
      System.out.println("DONE.");
    }
  }

  private static void doMain(ApplicationContext context) {
    final Runnable appRunner = context.getBean(Runnable.class);
    appRunner.run();
  }
}
