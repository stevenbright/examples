package com.alexshabanov;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point.
 */
public final class App {
    public static void main(String[] args) {
        System.out.println("PG Lock Test");

        final AbstractApplicationContext context = new ClassPathXmlApplicationContext("/spring/internal/runner-context.xml");

        try {
            final Runnable serviceRunner = context.getBean(Runnable.class);
            serviceRunner.run();
        } finally {
            context.close();
        }
    }
}
