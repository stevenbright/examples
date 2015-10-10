package com.alexshabanov.gjmdemo.server.config;

import com.alexshabanov.gjmdemo.logic.aspect.LoggerAspect;
import com.alexshabanov.gjmdemo.logic.service.UserService;
import com.alexshabanov.gjmdemo.logic.service.mapper.UserMapper;
import com.alexshabanov.gjmdemo.logic.service.helper.DbInitializer;
import com.alexshabanov.gjmdemo.logic.service.support.DefaultUserService;
import com.alexshabanov.gjmdemo.server.resource.SampleResource;
import com.alexshabanov.gjmdemo.server.resource.UserResource;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Example Guice Server configuration. Creates an Injector, and binds it to
 * whatever Modules we want. In this case, we use an anonymous Module, but other
 * modules are welcome as well.
 */
public final class ServerConfig extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    final Properties myBatisProperties = new Properties();
    myBatisProperties.setProperty("mybatis.environment.id", "test");
    myBatisProperties.setProperty("JDBC.driver", "org.h2.Driver");
    myBatisProperties.setProperty("JDBC.url", "jdbc:h2:mem:DaoTest-user-schema;DB_CLOSE_DELAY=-1");
    myBatisProperties.setProperty("JDBC.username", "sa");
    myBatisProperties.setProperty("JDBC.password", "");
    myBatisProperties.setProperty("JDBC.autoCommit", "false");

    myBatisProperties.setProperty("gjmDemo.dao.diagScripts", "gjmDemo/sql/user-diag.sql");
    myBatisProperties.setProperty("gjmDemo.dao.initScripts", "gjmDemo/sql/user-schema.sql,gjmDemo/sql/user-fixture.sql");

    final Injector injector = Guice.createInjector(
        // DAO
        new DaoModule(myBatisProperties),

        // Business Logic
        new AbstractModule() {
          @Override
          protected void configure() {
            // services
            bind(UserService.class).to(DefaultUserService.class);

            bindInterceptor(Matchers.inPackage(DefaultUserService.class.getPackage()),
                Matchers.any(),
                new LoggerAspect());
          }
        },

        // Server-specific
        new ServletModule() {
          @Override
          protected void configureServlets() {
            bind(SampleResource.class);
            bind(UserResource.class);


            // bind jackson converters for JAXB/JSON serialization
            bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
            bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);

            final Map<String, String> containerProps = new HashMap<>();
            if (Boolean.valueOf(System.getProperty("enableJettyTrace"))) {
              containerProps.put("com.sun.jersey.config.feature.Trace", "true");
            }

            serve("*").with(GuiceContainer.class, containerProps);
          }
        });

    injector.getInstance(DbInitializer.class).initialize();

    return injector;
  }
}