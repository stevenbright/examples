package com.alexshabanov.guiceresteasydemo;

import com.alexshabanov.guiceresteasydemo.config.ResourceModule;
import com.alexshabanov.guiceresteasydemo.filter.HelloFilter;
import com.alexshabanov.guiceresteasydemo.resource.HelloResource;
import com.alexshabanov.guiceresteasydemo.resource.UserResource;
import com.alexshabanov.guiceresteasydemo.servlet.EmptyServlet;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * @author Alexander Shabanov
 */
public class GuiceResteasyJettyFreemarkerDemoMain {

  public static void main(String[] args) throws Exception {
    // route JUL to SLF4J
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    final Injector injector = Guice.createInjector(new AppModule(), new ResourceModule());

    final Server server = new Server(8080);
    final ServletContextHandler servletHandler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);

    servletHandler.addServlet(EmptyServlet.class, "/*"); // TODO: remove

    servletHandler.addEventListener(injector.getInstance(GuiceResteasyBootstrapServletContextListener.class));

    final ServletHolder resteasyServletHolder = new ServletHolder(HttpServletDispatcher.class);
    //servletHandler.setInitParameter("resteasy.role.based.security", "true");
    if (args.length > 2) {
      servletHandler.addFilter(new FilterHolder(injector.getInstance(HelloFilter.class)), "/*", null);
    }
    servletHandler.addServlet(resteasyServletHolder, "/rest/*");

    server.setHandler(servletHandler);
    server.start();
    server.join();
  }

  private static final class AppModule extends RequestScopeModule implements Module {

    @Override
    protected void configure() {
      super.configure();
      bind(HelloFilter.class);

      // resources
      bind(HelloResource.class);
      bind(UserResource.class);

      // mappers
      final ObjectMapper mapper = new ObjectMapper(); // TODO: extra mapper settings
      mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
      // json provider (reader/writer)
      final JacksonJsonProvider jsonProvider = new JacksonJsonProvider(mapper);
      // bind jackson converters for JAXB/JSON serialization
      bind(JacksonJsonProvider.class).toInstance(jsonProvider);
    }
  }
}
