package com.alexshabanov.guiceresteasydemo;

import com.alexshabanov.guiceresteasydemo.config.ResourceModule;
import com.alexshabanov.guiceresteasydemo.launcher.GuiceResteasyLauncher;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * @author Alexander Shabanov
 */
public class GuiceResteasyDemoMain {

  public static void main(String[] args) {
    // route JUL to SLF4J
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    final Injector injector = Guice.createInjector(new ResourceModule());

    final GuiceResteasyLauncher launcher = new GuiceResteasyLauncher(injector);
    launcher.start();
  }
}
