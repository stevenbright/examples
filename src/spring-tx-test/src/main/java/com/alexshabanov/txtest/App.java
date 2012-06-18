package com.alexshabanov.txtest;

import com.alexshabanov.txtest.service.AppService;
import com.alexshabanov.txtest.service.DbaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.sql.SQLException;
import java.util.Map;

/**
 * Entry point.
 */
public final class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Configuration
    @ComponentScan(basePackages = "com.alexshabanov.txtest.service.support")
    public static class AppConfig {

    }

    @Configuration
    @EnableTransactionManagement(mode= AdviceMode.ASPECTJ)
    public static class DbConfig {

        @Inject
        private DataSource dataSource;

        @Bean
        public JdbcOperations jdbcOperations() {
            return new JdbcTemplate(dataSource);
        }

        @Configuration
        @Profile("default")
        static class DefaultDataSourceConfig {

            @Bean(destroyMethod="shutdown")
            public DataSource dataSource() {
                EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();
                factory.setDatabaseName("greenhouse");
                factory.setDatabaseType(EmbeddedDatabaseType.H2);
                return populateDatabase(factory.getDatabase());
            }

            @Bean
            public ResourceDatabasePopulator populator() {
                return new ResourceDatabasePopulator();
            }

            private EmbeddedDatabase populateDatabase(EmbeddedDatabase database) {
                // populate db
                final ResourceDatabasePopulator populator = populator();
                populator.setSqlScriptEncoding("UTF-8");
                populator.addScript(new ClassPathResource("sql/test-data.sql"));
                DatabasePopulatorUtils.execute(populator, database);

                return database;
            }
        }
    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, DbConfig.class);
        System.out.println("spring-tx-text start");

        try {
            context.start();

            if (LOGGER.isDebugEnabled()) {
                final Map<String, Object> beanDefMap = context.getBeansOfType(Object.class);
                LOGGER.debug("Bean definitions: {}", beanDefMap);
            }

            // run application service
            context.getBean(AppService.class).run(args);

            context.getBean(DbaService.class).doDbAccess();
        } finally {
            context.close();
        }
    }
}
