package com.alexshabanov;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Entry point.
 */
public final class App {

    private static void dumpProperties(ApplicationContext context) {
        final Map<?, ?> appProperties = (Map<?, ?>) context.getBean("appProperties", Map.class);

        System.out.println("\tDumping app context:");
        for (final Map.Entry entry : appProperties.entrySet()) {
            System.out.println("\t\t" + entry.getKey() + "=" + entry.getValue());
        }
    }

    public static void main(String[] args) {
        System.out.println("PG Lock Test");

        final AbstractApplicationContext context = new ClassPathXmlApplicationContext("/spring/app-context.xml");

        try {
            dumpProperties(context);
            final Runnable serviceRunner = context.getBean(Runnable.class);
            serviceRunner.run();
        } finally {
            context.close();
        }
    }
}
