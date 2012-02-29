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

    public static void main(String[] args) throws InterruptedException {
        final ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/service-context.xml");

        System.out.println("Spring Integration Demo");

        applicationContext.refresh();
        applicationContext.start();
        try {
            LOG.info("Starting context!");
            applicationContext.getBean("launcherService", Runnable.class).run();

            // sleep for a while before close to let all the pending message be processed
            Thread.sleep(500);
            LOG.info("Going to stop now!");
        } finally {
            applicationContext.close();
        }
    }
}
