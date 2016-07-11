package com.alexshabanov.bdbsample.demo;

import com.alexshabanov.bdbsample.dao.Mappers;
import com.alexshabanov.bdbsample.model.Blog;
import com.google.inject.Inject;
import com.google.protobuf.ByteString;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.truward.bdb.support.BdbDatabaseAccessSupport;
import com.truward.bdb.support.key.KeyUtil;
import com.truward.bdb.support.map.BdbMapDao;
import com.truward.bdb.support.map.MapDaoConfig;
import com.truward.bdb.support.protobuf.ProtobufBdbMapDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An illustration on how concurrent updates SHOULD NOT be done to the field that should account previous changes
 *
 * @author Alexander Shabanov
 */
public final class ConcurrentBlogEntryUpdates extends BdbDatabaseAccessSupport implements Runnable {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  public ConcurrentBlogEntryUpdates(@Nonnull Environment environment, @Nonnull DatabaseConfig databaseConfig) {
    super(environment, databaseConfig);
  }

  @Override
  public void run() {
    log.info("Hello");
    try {
      runConcurrentUpdatesWrongWay();
    } catch (Exception e) {
      log.error("RunDB error", e);
    }
  }

  //
  // Private
  //

  private void runConcurrentUpdatesWrongWay() {
    final Database db = openDatabase("blogEntriesConcurrentWrong");
    final BdbMapDao<Blog.BlogEntry> blogEntryDao = new ProtobufBdbMapDaoSupport<>(new MapDaoConfig<>(db,
        Mappers.BLOG_ENTRY_MAPPER));

    final ByteString key = KeyUtil.randomKey();
    blogEntryDao.put(null, key, Blog.BlogEntry.newBuilder().setTitle("First Post").build());

    log.info("Inserted initial record: {}", blogEntryDao.get(null, key));

    // statistics
    final AtomicInteger conflictCount = new AtomicInteger();

    // Assign 1000 likes in 50 threads
    final int execTimes = 20;
    final int threadCount = 50;

    final CountDownLatch startGate = new CountDownLatch(1); // for thread starts
    final CountDownLatch endGate = new CountDownLatch(threadCount); // for awaiting until all thread complete

    for (int i = 0; i < threadCount; ++i) {
      final int threadIndex = i;
      final Thread thread = new Thread(() -> {
        log.info("Running thread #{}", threadIndex);
        try {
          startGate.await();

          runTask(blogEntryDao, key, execTimes, conflictCount);

        } catch (InterruptedException ignored) {
          Thread.currentThread().interrupt(); // TODO: why is this important here?
          log.error("Unexpected interruption");
        } finally {
          endGate.countDown();
        }
      });
      thread.start();
    }

    // start threads and await for completion
    final long start = System.currentTimeMillis();
    startGate.countDown();
    try {
      endGate.await();
      final long end = System.currentTimeMillis();
      log.info("All threads should be stopped by now, time: {} msec", end - start);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Unexpected interruption");
    }

    // verify updates
    final Blog.BlogEntry finalBlogEntry = blogEntryDao.get(null, key);
    log.info("Likes = {}, total conflicts={}", finalBlogEntry.getLikes(), conflictCount.get());
  }

  private void runTask(BdbMapDao<Blog.BlogEntry> blogEntryDao,
                       ByteString key,
                       int execTimes,
                       AtomicInteger conflictCount) throws InterruptedException {
    for (int i = 0; i < execTimes; ++i) {

      // Illustration on how to do optimistic locking in a wrong way
      // NOTE: This is a WRONG approach, it is here to show high contention
      for (;;) {
        // [1] acquire record
        final Blog.BlogEntry original = blogEntryDao.get(null, key);
        if (original.getVersion().isEmpty()) { // does somebody acquired this record before?
          // ok - version is empty, looks like we are the ones who acquired it first
          final ByteString versionKey = KeyUtil.randomKey();

          // [2] assign update version
          blogEntryDao.put(null, key, Blog.BlogEntry.newBuilder(original)
              .setVersion(versionKey)
              .build());

          // [3] retrieve version once more
          final Blog.BlogEntry entry = blogEntryDao.get(null, key);
          if (entry.getVersion().equals(versionKey)) {
            // [4] same version, proceed with update
            blogEntryDao.put(null, key, Blog.BlogEntry.newBuilder(original)
                .setLikes(original.getLikes() + 1)
                .setVersion(ByteString.EMPTY).build());
            break;
          }
        }
        conflictCount.incrementAndGet(); // CAS failed, do it all over again
      }
    }
  }
}
