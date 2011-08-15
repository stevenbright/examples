package com.alexshabanov.txtest;

import com.alexshabanov.txtest.service.AppRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point.
 */
public final class App {

    private static void doMain(ApplicationContext applicationContext) {
        final Runnable runner = applicationContext.getBean(AppRunner.class);
        runner.run();
    }

    public static void main(String[] args) {
        System.out.println("In tx concurrency test app!");
        final AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:/spring/app-context.xml");

        try {
            doMain(applicationContext);
            System.out.println("App OK.");
        } finally {
            applicationContext.close();
        }
    }
}
