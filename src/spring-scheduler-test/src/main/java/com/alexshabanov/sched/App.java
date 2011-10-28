package com.alexshabanov.sched;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point.
 */
public final class App {
    public static void main(String[] args) {
        System.out.println("Spring scheduler tester!");

        final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
                "classpath:/spring/service-context.xml"
        });

        try {
            final Runnable appRunner = context.getBean("appRunner", Runnable.class);
            appRunner.run();
        } finally {
            context.close();
        }
    }
}
