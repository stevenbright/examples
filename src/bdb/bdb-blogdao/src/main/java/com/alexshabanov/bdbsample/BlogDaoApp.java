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
import com.truward.bdb.support.transaction.BdbTransactionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class BlogDaoApp extends BdbTransactionSupport implements Runnable {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final DatabaseConfig dbConfig;

  @Inject
  public BlogDaoApp(@Nonnull Environment env, @Nonnull DatabaseConfig dbConfig) {
    super(env);
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
    final Database dbBlogEntries = getEnvironment().openDatabase(null, "blogEntries", dbConfig);
    final BdbMapDao<Blog.BlogEntry> blogEntryMap = new ProtobufBdbMapDaoSupport<>(dbBlogEntries,
        (k, v) -> Blog.BlogEntry.parseFrom(v.getData()), LockMode.READ_COMMITTED);

    final ByteString key = ByteString.copyFrom("1", StandardCharsets.UTF_8);

    // put 1
    blogEntryMap.put(null, key, Blog.BlogEntry.newBuilder().setTitle("54321").build());
    Blog.BlogEntry actualValue = blogEntryMap.get(null, key);
    log.info("[1] value={}", actualValue);

    // put 2.1
    blogEntryMap.put(null, key, Blog.BlogEntry.newBuilder().setTitle("12345678").build());
    actualValue = blogEntryMap.get(null, key);
    log.info("[2.1] value={}", actualValue);

    // put 2.2
    if (actualValue.getTitle().startsWith("54")) {
      final DatabaseEntry entry = new DatabaseEntry(key.toByteArray());
      DatabaseEntry value = new DatabaseEntry(Blog.BlogEntry.newBuilder().setTitle("12345678").build().toByteArray());
      dbBlogEntries.put(null, entry, value);
      final DatabaseEntry out = new DatabaseEntry();
      dbBlogEntries.get(null, entry, out, LockMode.READ_COMMITTED);
      log.info("[2.2] out={}", Blog.BlogEntry.parseFrom(out.getData()));
    }

    // put 2.3
    withTransactionVoid((tx) -> blogEntryMap.put(tx, key, Blog.BlogEntry.newBuilder().setTitle("12345678").build()));
    actualValue = withTransaction((tx) -> blogEntryMap.get(tx, key));
    log.info("[2.3] value={}", actualValue);

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
      bind(BlogDaoApp.class);
    }
  }

  public static void main(String[] args) {
    final Injector injector = Guice.createInjector(new AppModule(), new BdbModule());
    final Runnable runnableApp = injector.getInstance(BlogDaoApp.class);
    final Cleanup cleanup = injector.getInstance(Cleanup.class);
    try {
      runnableApp.run();
    } finally {
      cleanup.run();
    }
  }
}
