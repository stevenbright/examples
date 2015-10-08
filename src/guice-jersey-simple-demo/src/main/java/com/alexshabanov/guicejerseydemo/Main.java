package com.alexshabanov.guicejerseydemo;

import com.alexshabanov.guicejerseydemo.server.EmptyServlet;
import com.alexshabanov.guicejerseydemo.server.config.ServerConfig;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Server entry point.
 */
public class Main {
  public static void main(String[] args) throws Exception {
    // route JUL to SLF4J
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    // start jetty
    final Server server = new Server(8080);
    final ServletContextHandler root = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);

    root.addEventListener(new ServerConfig());
    root.addFilter(GuiceFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    root.addServlet(EmptyServlet.class, "/*");

    server.start();
  }
}
