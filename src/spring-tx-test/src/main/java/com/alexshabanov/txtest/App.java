package com.alexshabanov.txtest;

import com.alexshabanov.txtest.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Entry point.
 */
public final class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Configuration
    @ComponentScan(basePackages = "com.alexshabanov.txtest.service.support")
    public static class Config {}

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        System.out.println("spring-tx-text start");

        try {
            context.start();

            if (LOGGER.isDebugEnabled()) {
                final Map<String, Object> beanDefMap = context.getBeansOfType(Object.class);
                LOGGER.debug("Bean definitions: {}", beanDefMap);
            }

            // run application service
            context.getBean(AppService.class).run(args);
        } finally {
            context.close();
        }
    }
}
