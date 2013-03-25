package com.alexshabanov.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

/**
 * Entry point to the Spring Command Line Interface (CLI) Application Demo.
 *
 * Start application with parameter --url=something to see that property source does work;
 *
 * Run application (after mvn clean package assembly:assembly) -
 *  java -jar target/cli-demo-jar-with-dependencies.jar --url=134134
 */
public final class CliDemoApp {
    public static final Logger LOG = LoggerFactory.getLogger(CliDemoApp.class);

    public static final String URL_PARAM_KEY = "url";


    public static void main(String[] args) {
        LOG.trace("CLI demo app");

        // should use parameterless constructor as the other one invoke refresh which we certainly don't want
        // as it automatically trigger property injection and our CLI property is not ready yet
        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // setup configuration
        applicationContext.register(Config.class);

        // add CLI property source
        applicationContext.getEnvironment().getPropertySources().addLast(new SimpleCommandLinePropertySource(args));

        // setup all the dependencies (refresh) and make them run (start)
        applicationContext.refresh();
        applicationContext.start();

        try {
            applicationContext.getBean(Runnable.class).run();
        } finally {
            applicationContext.close();
        }
    }

    @Configuration
    public static class Config {
        @Autowired
        private Environment environment;

        @Bean
        public Runnable myBean() {
            return new MyBean(environment.getProperty(URL_PARAM_KEY, "http://127.0.0.1:8080"));
        }
    }

    public static final class MyBean implements Runnable {
        private final String url;

        public MyBean(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            LOG.info("MyBean run with url = {}", url);
        }
    }
}
