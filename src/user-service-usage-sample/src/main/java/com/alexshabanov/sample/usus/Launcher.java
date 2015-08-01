package com.alexshabanov.sample.usus;

import com.truward.brikar.server.launcher.StandardLauncher;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry point
 */
public final class Launcher extends StandardLauncher {
  private final ResourceHandler resourceHandler;

  public Launcher() throws IOException {
    super("classpath:/ususService/");
    this.resourceHandler = createStaticHandler(null);
  }

  public static void main(String[] args) throws Exception {
    new Launcher().start(args);
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

  //
  // Private
  //

  private ResourceHandler createStaticHandler(@Nullable String overrideStaticPath) throws IOException {
    final ResourceHandler resourceHandler = new ResourceHandler();
    if (overrideStaticPath != null) {
      getLogger().info("Using override path for static resources: {}", overrideStaticPath);
      resourceHandler.setBaseResource(Resource.newResource(new File(overrideStaticPath)));
    } else {
      resourceHandler.setBaseResource(Resource.newClassPathResource("/ususService/web/static"));
    }
    return resourceHandler;
  }
}
