package com.alexshabanov.springintdemo;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point.
 */
public final class App {
    public static void main(String[] args) {
        final ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/service-context.xml");

        System.out.println("Spring Integration Demo");

        applicationContext.refresh();
        applicationContext.start();
        try {
            applicationContext.getBean("launcherService", Runnable.class).run();
        } finally {
            applicationContext.close();
        }
    }
}
