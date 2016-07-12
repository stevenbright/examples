package com.alexshabanov.bdbsample;

import com.alexshabanov.bdbsample.demo.BlogEntryMapDemo;
import com.alexshabanov.bdbsample.demo.ConcurrentBlogEntryUpdates;
import com.alexshabanov.bdbsample.helper.Cleanup;
import com.alexshabanov.bdbsample.module.BdbModule;
import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.List;
import java.util.Objects;

public final class BlogDaoApp {

  // app configuration
  public static final class AppModule extends AbstractModule {
    private final List<String> args;

    public AppModule(String[] args) {
      this.args = ImmutableList.copyOf(Objects.requireNonNull(args));
    }

    @Override
    protected void configure() {
      if (args.contains("blogEntryMapDemo")) {
        bind(Runnable.class).to(BlogEntryMapDemo.class);
      } else if (args.contains("concurrentBlogEntryUpdates")) {
        bind(Runnable.class).to(ConcurrentBlogEntryUpdates.class);
      } else {
        // default mode
        bind(Runnable.class).to(ConcurrentBlogEntryUpdates.class);
      }
    }
  }

  public static void main(String[] args) {
    final Injector injector = Guice.createInjector(new AppModule(args), new BdbModule());
    final Runnable runnableApp = injector.getInstance(Runnable.class);
    final Cleanup cleanup = injector.getInstance(Cleanup.class);
    try {
      runnableApp.run();
    } finally {
      cleanup.run();
    }
  }
}
