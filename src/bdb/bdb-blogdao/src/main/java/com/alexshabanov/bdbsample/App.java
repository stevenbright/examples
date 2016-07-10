package com.alexshabanov.bdbsample;

import com.alexshabanov.bdbsample.helper.Cleanup;
import com.alexshabanov.bdbsample.model.Blog;
import com.alexshabanov.bdbsample.module.BdbModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.protobuf.ByteString;
import com.sleepycat.je.*;
import com.truward.bdb.support.map.BdbMapDao;
import com.truward.bdb.support.protobuf.ProtobufBdbMapDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class App implements Runnable {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final Environment env;
  private final DatabaseConfig dbConfig;

  @Inject
  public App(Environment env, DatabaseConfig dbConfig) {
    this.env = Objects.requireNonNull(env, "env");
    this.dbConfig = Objects.requireNonNull(dbConfig, "dbConfig");
  }

  @Override
  public void run() {
    log.info("Hello");
    try {
      runDb();
    } catch (Exception e) {
      log.error("RunDB error", e);
    }
  }

  private void runDb() throws Exception {
    final Database dbBlogEntries = env.openDatabase(null, "blogEntries", dbConfig);
    final BdbMapDao<Blog.BlogEntry> blogEntryMap = new ProtobufBdbMapDaoSupport<>(dbBlogEntries,
        (k, v) -> Blog.BlogEntry.parseFrom(v.getData()));

    final ByteString key = ByteString.copyFrom("1", StandardCharsets.UTF_8);

    // put 1
    blogEntryMap.put(null, key, Blog.BlogEntry.newBuilder().setTitle("54321").build());
    Blog.BlogEntry actualValue = blogEntryMap.get(null, key);
    log.info("[1] value={}", actualValue);

    // put 2
    blogEntryMap.put(null, key, Blog.BlogEntry.newBuilder().setTitle("12345678").build());
    actualValue = blogEntryMap.get(null, key);
    log.info("[2] value={}", actualValue);

    // put 3
    blogEntryMap.put(null, key, Blog.BlogEntry.newBuilder().setTitle("789").build());
    actualValue = blogEntryMap.get(null, key);
    log.info("[3] value={}", actualValue);

    // delete
    blogEntryMap.delete(null, key);
    actualValue = blogEntryMap.get(null, key, Blog.BlogEntry::getDefaultInstance);
    log.info("[4] value={}", actualValue);

    // put 4
    blogEntryMap.put(null, key, Blog.BlogEntry.newBuilder().setTitle("9 8765 4321 0").build());
    actualValue = blogEntryMap.get(null, key);
    log.info("[5] value={}", actualValue);
  }

  // app configuration
  public static final class AppModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(App.class);
    }
  }

  public static void main(String[] args) {
    final Injector injector = Guice.createInjector(new AppModule(), new BdbModule());
    final Runnable runnableApp = injector.getInstance(App.class);
    final Cleanup cleanup = injector.getInstance(Cleanup.class);
    try {
      runnableApp.run();
    } finally {
      cleanup.run();
    }
  }
}
