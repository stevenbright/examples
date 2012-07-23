package com.alexshabanov.springjmsdemo;

import com.alexshabanov.springjmsdemo.config.BeansConfig;
import com.alexshabanov.springjmsdemo.config.JmsConfig;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Entry point.
 */
public final class App {
    public static void main(String[] args) throws InterruptedException {
        final ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(
                JmsConfig.class,
                BeansConfig.class
        );

        System.out.println("Starting!");

        context.start();
        try {
            context.getBean("appRunner", Runnable.class).run();

            Thread.sleep(5000L);
        } finally {
            context.close();
        }

        System.out.println("-- EOZ --");
    }
}
