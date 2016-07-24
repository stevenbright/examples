package com.alexshabanov.guiceresteasydemo;

import com.alexshabanov.guiceresteasydemo.config.ResourceModule;
import com.alexshabanov.guiceresteasydemo.filter.RequestScopeAuthFilter;
import com.alexshabanov.guiceresteasydemo.servlet.EmptyServlet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.eclipse.jetty.server.Server;
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

    // TODO: implement auth filter
    //servletHandler.addFilter(new FilterHolder(injector.getInstance(RequestScopeAuthFilter.class)), "/rest/*", null);
    servletHandler.addServlet(resteasyServletHolder, "/rest/*");

    server.setHandler(servletHandler);
    server.start();
    server.join();
  }

  private static final class AppModule extends RequestScopeModule implements Module {

    @Override
    protected void configure() {
      super.configure();
      bind(RequestScopeAuthFilter.class);
    }
  }
}
