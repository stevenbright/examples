package com.alexshabanov.intdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point.
 */
public final class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException {
        final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:/app-context.xml");
        context.start();

        try {
            LOGGER.info("Let's get dangerous!");
            Thread.sleep(10L);

            LOGGER.info("Stopping, t={}", System.currentTimeMillis());
        } finally {
            context.close();
        }
    }
}
