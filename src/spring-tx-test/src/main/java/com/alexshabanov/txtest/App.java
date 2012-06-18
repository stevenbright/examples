package com.alexshabanov.txtest;

import com.alexshabanov.txtest.service.AppService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Entry point.
 */
public final class App {

    @Configuration
    @ComponentScan(basePackages = "com.alexshabanov.txtest.service.support")
    public static class Config {}

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        System.out.println("spring-tx-text start");

        try {
            context.start();

            final Map<String, Object> a = context.getBeansOfType(Object.class);
            a.toString();

            // run application service
            context.getBean(AppService.class).run(args);
        } finally {
            context.close();
        }
    }
}
