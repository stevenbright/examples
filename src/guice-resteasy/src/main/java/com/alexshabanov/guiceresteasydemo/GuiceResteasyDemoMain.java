package com.alexshabanov.guiceresteasydemo;

import org.jboss.resteasy.plugins.guice.GuiceResourceFactory;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.plugins.server.servlet.ConfigurationBootstrap;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * @author Alexander Shabanov
 */
public class GuiceResteasyDemoMain {

  public static void main(String[] args) {
    // route JUL to SLF4J
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    final ResteasyDeployment deployment = new ResteasyDeployment();

    // see https://github.com/jonyoder/Twist/blob/master/twist-server/src/main/java/com/fl/twist/guice/GuiceServletConfig.java

    // TODO: fix it!
    deployment.getResourceFactories().add(
        new GuiceResourceFactory(injector.getProvider(App.class),
            App.class));

    final NettyJaxrsServer netty = new NettyJaxrsServer();
    netty.setDeployment(deployment);
    netty.setPort(9090);
    netty.setRootResourcePath("");
    netty.setSecurityDomain(null);
    netty.start();
  }
}
