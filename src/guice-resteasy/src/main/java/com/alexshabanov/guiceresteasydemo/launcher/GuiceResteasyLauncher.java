package com.alexshabanov.guiceresteasydemo.launcher;

import com.google.inject.Injector;
import org.jboss.resteasy.plugins.guice.GuiceResourceFactory;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import javax.annotation.Nonnull;

/**
 * Launcher for guice application.
 */
public final class GuiceResteasyLauncher {

  private final Injector injector;

  public GuiceResteasyLauncher(@Nonnull Injector injector) {
    this.injector = injector;
  }

  public void start() {
    final ResteasyDeployment deployment = new ResteasyDeployment();

    final JaxrsAssetInspector assetInspector = injector.getInstance(JaxrsAssetInspector.class);
    for (final Class<?> resClass : assetInspector.getResourceClasses()) {
      deployment.getResourceFactories().add(new GuiceResourceFactory(injector.getProvider(resClass), resClass));
    }

    final NettyJaxrsServer nettyJaxrsServer = new NettyJaxrsServer();
    nettyJaxrsServer.setDeployment(deployment);
    nettyJaxrsServer.setPort(9090);
    nettyJaxrsServer.setRootResourcePath("");
    nettyJaxrsServer.setSecurityDomain(null);

    nettyJaxrsServer.start();
  }
}
