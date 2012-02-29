package com.alexshabanov.springintdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point.
 */
public final class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        final ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/service-context.xml");

        System.out.println("Spring Integration Demo");

        applicationContext.refresh();
        applicationContext.start();
        try {
            LOG.info("Starting context!");
            applicationContext.getBean("launcherService", Runnable.class).run();
        } finally {
            applicationContext.close();
        }
    }
}
