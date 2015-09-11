package com.alexshabanov.sample.brikar.website;

import com.truward.brikar.server.launcher.StandardLauncher;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry point
 */
public final class Launcher extends StandardLauncher {
  private final ResourceHandler resourceHandler;

  public Launcher() throws Exception {
    super("classpath:/demoWebsite/");
    this.resourceHandler = createStaticHandler();
  }

  public static void main(String[] args) throws Exception {
    try (Launcher launcher = new Launcher()) {
      launcher.start();
    }
  }

  @Override
  protected boolean isSpringSecurityEnabled() {
    return true;
  }

  @Nonnull
  @Override
  protected List<Handler> getHandlers() {
    final List<Handler> handlers = new ArrayList<>(super.getHandlers());
    handlers.add(resourceHandler);
    return handlers;
  }

  @Override
  protected int getServletContextOptions() {
    return ServletContextHandler.SESSIONS;
  }

  //
  // Private
  //

  @Nonnull
  private ResourceHandler createStaticHandler() throws IOException {
    final ResourceHandler resourceHandler = new ResourceHandler();

    final String overrideStaticPath = getPropertyResolver().getProperty("brikar.dev.overrideStaticPath");
    if (overrideStaticPath != null) {
      getLogger().info("Using override path for static resources: {}", overrideStaticPath);
      resourceHandler.setBaseResource(Resource.newResource(new File(overrideStaticPath)));
    } else {
      resourceHandler.setBaseResource(Resource.newClassPathResource("/demoWebsite/web/static"));
    }

    return resourceHandler;
  }
}
