package com.alexshabanov.bdbsample;

import com.alexshabanov.bdbsample.demo.BlogEntryMapDemo;
import com.alexshabanov.bdbsample.helper.Cleanup;
import com.alexshabanov.bdbsample.model.Blog;
import com.alexshabanov.bdbsample.module.BdbModule;
import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.protobuf.ByteString;
import com.sleepycat.je.*;
import com.truward.bdb.support.map.BdbMapDao;
import com.truward.bdb.support.protobuf.ProtobufBdbMapDaoSupport;
import com.truward.bdb.support.transaction.BdbTransactionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
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
      } else {
        // default mode
        bind(Runnable.class).to(BlogEntryMapDemo.class);
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
