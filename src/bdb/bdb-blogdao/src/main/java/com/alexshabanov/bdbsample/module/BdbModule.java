package com.alexshabanov.bdbsample.module;

import com.alexshabanov.bdbsample.helper.Cleanup;
import com.google.inject.AbstractModule;
import com.sleepycat.je.CacheMode;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Alexander Shabanov
 */
public final class BdbModule extends AbstractModule {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  protected void configure() {
    bindEnvironment();
    bind(DatabaseConfig.class).toInstance(dbConfig());
  }

  //
  // Private
  //

  private void bindEnvironment() {
    final String envDir = System.getProperty("java.io.tmpdir");
    final File envHome = new File(envDir, "BDB-" + System.currentTimeMillis() + "-" + ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
    if (!envHome.mkdir()) {
      throw new IllegalStateException("Can't create temp env");
    }

    final EnvironmentConfig environmentConfig = new EnvironmentConfig();
    environmentConfig.setAllowCreate(true);
    environmentConfig.setTransactional(true);

    final Environment environment = new Environment(envHome, environmentConfig);
    log.info("Created environment at {}", envHome.getAbsolutePath());
    bind(Environment.class).toInstance(environment);
    bind(Cleanup.class).toInstance(new EnvironmentCleanup(envHome));
  }

  private DatabaseConfig dbConfig() {
    return new DatabaseConfig()
        .setTransactional(true)
        .setAllowCreate(true)
        .setSortedDuplicates(false)
        .setDeferredWrite(false) // SYNC writes
        .setCacheMode(CacheMode.DEFAULT);
  }

  private static final class EnvironmentCleanup implements Cleanup {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final File envHome;

    private EnvironmentCleanup(@Nonnull File envHome) {
      this.envHome = envHome;
    }

    @Override
    public void run() {
      try {
        deleteRecursively(envHome);
        log.info("Cleanup completed, {} deleted", envHome.getAbsolutePath());
      } catch (IOException e) {
        log.error("Error while doing final BDB environment cleanup", e);
      }
    }

    private static void deleteRecursively(File envHome) throws IOException {
      final Path envHomePath = envHome.toPath();
      Files.walkFileTree(envHomePath, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          Files.delete(file);
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
          Files.delete(dir);
          return FileVisitResult.CONTINUE;
        }
      });
      Files.deleteIfExists(envHomePath);
    }
  }
}
