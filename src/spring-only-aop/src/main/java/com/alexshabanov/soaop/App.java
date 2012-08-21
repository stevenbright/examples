package com.alexshabanov.soaop;

import com.alexshabanov.soaop.service.support.DefaultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point.
 */
public final class App implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void run() {
        LOGGER.info("In-context activation");
    }

    @Configuration
    @ComponentScan(basePackageClasses = DefaultService.class)
    public static class Config {

        @Bean
        public Runnable app() {
            return new App();
        }
    }


    public static void main(String[] args) {
        LOGGER.info("Activating!");

        final ConfigurableApplicationContext context;
        if (args.length > 1 && "--class-config".equals(args[0])) {
            // requires CGLIB
            context = new AnnotationConfigApplicationContext(Config.class);
        } else {
            // requires AspectJ
            context = new ClassPathXmlApplicationContext("classpath:/spring/app-context.xml");
        }

        context.start();

        try {
            context.getBean(Runnable.class).run();
        } finally {
            LOGGER.info("Stopping");
            context.close();
        }
    }
}
