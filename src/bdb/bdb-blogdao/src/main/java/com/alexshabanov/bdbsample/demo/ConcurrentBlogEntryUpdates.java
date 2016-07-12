package com.alexshabanov.bdbsample.demo;

import com.alexshabanov.bdbsample.model.Blog;
import com.google.common.primitives.Longs;
import com.google.inject.Inject;
import com.google.protobuf.ByteString;
import com.google.protobuf.BytesValue;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.truward.bdb.support.BdbDatabaseAccessSupport;
import com.truward.bdb.support.key.KeyUtil;
import com.truward.bdb.support.map.BdbMapDao;
import com.truward.bdb.support.map.MapDaoConfig;
import com.truward.bdb.support.mapper.ProtobufMappers;
import com.truward.bdb.support.protobuf.ProtobufBdbMapDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * An illustration on how concurrent updates SHOULD NOT be done to the field that should account previous changes.
 *
 * Sample run:
 * <pre>
 * ... - [WrongWay1] All threads should be stopped by now, time: 4630 msec
 * ... - [WrongWay1] Likes = 498, total conflicts=316582
 *
 * ... - [WrongWay2] All threads should be stopped by now, time: 66235 msec
 * ... - [WrongWay2] Likes = 1000, total conflicts=64198
 *
 * ... - [Right] All threads should be stopped by now, time: 14 msec
 * ... - [Right] Likes = 1000
 * </pre>
 *
 * @author Alexander Shabanov
 */
public final class ConcurrentBlogEntryUpdates extends BdbDatabaseAccessSupport implements Runnable {

  private static final int MAX_RETRIES = 100000;

  // Assign 1000 likes (20 times in 50 threads)
  private final int execTimes = 20;
  private final int threadCount = 50;

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  public ConcurrentBlogEntryUpdates(@Nonnull Environment environment, @Nonnull DatabaseConfig databaseConfig) {
    super(environment, databaseConfig);
  }

  @Override
  public void run() {
    try {
      runConcurrentUpdatesWrongWay1();
      runConcurrentUpdatesWrongWay2();
      runConcurrentUpdatesRightWay1();
    } catch (Exception e) {
      log.error("RunDB error", e);
    }
  }

  //
  // Private
  //

  private void runConcurrentUpdatesWrongWay1() {
    final Database db = openDatabase("blogEntriesConcurrentWrong");
    final BdbMapDao<Blog.BlogEntry> blogEntryDao = new ProtobufBdbMapDaoSupport<>(new MapDaoConfig<>(db,
        ProtobufMappers.of(Blog.BlogEntry.getDefaultInstance())));

    final ByteString key = KeyUtil.randomKey();
    blogEntryDao.put(null, key, Blog.BlogEntry.newBuilder().setTitle("First Post").build());

    log.info("[WrongWay1] Inserted initial record: {}", blogEntryDao.get(null, key));

    // statistics
    final AtomicInteger conflictCount = new AtomicInteger();

    final CountDownLatch startGate = new CountDownLatch(1); // for thread starts
    final CountDownLatch endGate = new CountDownLatch(threadCount); // for awaiting until all thread complete

    for (int i = 0; i < threadCount; ++i) {
      final int threadIndex = i;
      final Thread thread = new Thread(() -> {
        log.debug("Running thread #{}", threadIndex);
        try {
          startGate.await();

          runWrongUpdateTask1(blogEntryDao, key, conflictCount);

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
      log.info("[WrongWay1] All threads should be stopped by now, time: {} msec", end - start);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Unexpected interruption");
    }

    // verify updates
    final Blog.BlogEntry finalBlogEntry = blogEntryDao.get(null, key);
    log.info("[WrongWay1] Likes = {}, total conflicts={}", finalBlogEntry.getLikes(), conflictCount.get());
  }

  private void runWrongUpdateTask1(BdbMapDao<Blog.BlogEntry> blogEntryDao,
                                   ByteString key,
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

  private void runConcurrentUpdatesWrongWay2() {
    final Database dbEntries = openDatabase("blogEntriesConcurrentWrong2");
    final BdbMapDao<Blog.BlogEntry> blogEntryDao = new ProtobufBdbMapDaoSupport<>(new MapDaoConfig<>(dbEntries,
            ProtobufMappers.of(Blog.BlogEntry.getDefaultInstance())));

    final DatabaseConfig dbLockConfig = new DatabaseConfig();
    dbLockConfig.setTransactional(false);
    dbLockConfig.setSortedDuplicates(true);
    dbLockConfig.setAllowCreate(true);
    dbLockConfig.setKeyPrefixing(true);
    final Database dbLock = getEnvironment().openDatabase(null, "blogEntriesLock", dbLockConfig);
    final BdbMapDao<BytesValue> lockDao = new ProtobufBdbMapDaoSupport<>(new MapDaoConfig<>(dbLock,
            ProtobufMappers.of(BytesValue.getDefaultInstance())));

    final ByteString key = KeyUtil.randomKey();
    blogEntryDao.put(null, key, Blog.BlogEntry.newBuilder().setTitle("First Post").build());

    log.info("[WrongWay2] Inserted initial record: {}", blogEntryDao.get(null, key));

    // statistics
    final AtomicInteger conflictCount = new AtomicInteger();

    final CountDownLatch startGate = new CountDownLatch(1); // for thread starts
    final CountDownLatch endGate = new CountDownLatch(threadCount); // for awaiting until all thread complete

    for (int i = 0; i < threadCount; ++i) {
      final int threadIndex = i;
      final Thread thread = new Thread(() -> {
        log.debug("Running thread #{}", threadIndex);
        try {
          startGate.await();

          runWrongUpdateTask2(lockDao, blogEntryDao, key, conflictCount);

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
      log.info("[WrongWay2] All threads should be stopped by now, time: {} msec", end - start);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Unexpected interruption");
    }

    // verify updates
    final Blog.BlogEntry finalBlogEntry = blogEntryDao.get(null, key);
    log.info("[WrongWay2] Likes = {}, total conflicts={}", finalBlogEntry.getLikes(), conflictCount.get());
  }

  private final AtomicLong versionId = new AtomicLong();

  // This is technically correct, but still considered as wrong, because of high contention
  private void runWrongUpdateTask2(BdbMapDao<BytesValue> dbLock,
                                   BdbMapDao<Blog.BlogEntry> blogEntryDao,
                                   ByteString key,
                                   AtomicInteger conflictCount) throws InterruptedException {
    for (int i = 0; i < execTimes; ++i) {

      // Optimistic lock, still causes a lot of contention
      boolean wasSuccessful = false;

      for (int retries = 0; retries < MAX_RETRIES; ++retries) {
        // [1] acquire record
        BytesValue lock = dbLock.get(null, key, () -> null);
        if (lock == null) { // does somebody acquired this record before?
          // ok - version is empty, looks like we are the ones who acquired it first
          // NOTE: we need big endian representation of long here!
          final ByteString versionKey = ByteString.copyFrom(Longs.toByteArray(versionId.incrementAndGet()));

          // [2] assign update version
          dbLock.put(null, key, BytesValue.newBuilder().setValue(versionKey).build());

          // [3] retrieve version once more, duplicates should be disregarded
          lock = dbLock.get(null, key, () -> null);
          if (lock != null && lock.getValue().equals(versionKey)) {
            // [4] same version, proceed with the update
            final Blog.BlogEntry original = blogEntryDao.get(null, key);
            blogEntryDao.put(null, key, Blog.BlogEntry.newBuilder(original)
                .setLikes(original.getLikes() + 1)
                .build());

            // [5] clear lock versions
            dbLock.delete(null, key);
            wasSuccessful = true;
            break;
          }
        }
        conflictCount.incrementAndGet(); // CAS failed, do it all over again
      } // optimistic lock retry loop

      if (!wasSuccessful) {
        throw new IllegalStateException();
      }
    }
  }

  private void runConcurrentUpdatesRightWay1() {
    final Database dbEntries = openDatabase("blogEntriesConcurrentRight1");
    final BdbMapDao<Blog.BlogEntry> blogEntryDao = new ProtobufBdbMapDaoSupport<>(new MapDaoConfig<>(dbEntries,
        ProtobufMappers.of(Blog.BlogEntry.getDefaultInstance())));

    final DatabaseConfig dbLockConfig = new DatabaseConfig();
    dbLockConfig.setTransactional(false);
    dbLockConfig.setSortedDuplicates(true);
    dbLockConfig.setAllowCreate(true);
    dbLockConfig.setKeyPrefixing(true);
    final Database dbLikeCount = getEnvironment().openDatabase(null, "blogEntriesLikeCount", dbLockConfig);
    final BdbMapDao<BytesValue> likeCountDao = new ProtobufBdbMapDaoSupport<>(new MapDaoConfig<>(dbLikeCount,
        ProtobufMappers.of(BytesValue.getDefaultInstance())));

    final ByteString key = KeyUtil.randomKey();
    blogEntryDao.put(null, key, Blog.BlogEntry.newBuilder().setTitle("First Post").build());

    log.info("[Right] Inserted initial record: {}", blogEntryDao.get(null, key));

    final CountDownLatch startGate = new CountDownLatch(1); // for thread starts
    final CountDownLatch endGate = new CountDownLatch(threadCount); // for awaiting until all thread complete

    for (int i = 0; i < threadCount; ++i) {
      final int threadIndex = i;
      final Thread thread = new Thread(() -> {
        log.debug("Running thread #{}", threadIndex);
        try {
          startGate.await();

          runRightUpdateTask1(likeCountDao, key);

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
      log.info("[Right] All threads should be stopped by now, time: {} msec", end - start);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Unexpected interruption");
    }

    // verify updates
    final List<BytesValue> likeEntries = likeCountDao.getAsList(null, key);
    log.info("[Right] Likes = {}, Entries={}", likeEntries.size(), likeEntries.stream().map(e -> KeyUtil.toHexString(e.getValue())).collect(Collectors.toList()));
  }

  private void runRightUpdateTask1(BdbMapDao<BytesValue> likeCount,
                                   ByteString key) throws InterruptedException {
    for (int i = 0; i < execTimes; ++i) {
      // need to create unique value to let BDB differ values
      likeCount.put(null, key, BytesValue.newBuilder().setValue(KeyUtil.randomKey(8)).build());
    }
  }
}
