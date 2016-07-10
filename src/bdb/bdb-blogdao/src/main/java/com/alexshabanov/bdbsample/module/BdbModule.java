package com.alexshabanov.bdbsample.module;

import com.google.inject.AbstractModule;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Alexander Shabanov
 */
public final class BdbModule extends AbstractModule {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  protected void configure() {
    bind(Environment.class).toInstance(createEnvironment());
    bind(DatabaseConfig.class).toInstance(dbConfig());
  }

  private Environment createEnvironment() {
    final File envHome;
    try {
      envHome = File.createTempFile("TestDB-", "-bdb");
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    final EnvironmentConfig environmentConfig = new EnvironmentConfig();
    environmentConfig.setAllowCreate(true);
    environmentConfig.setTransactional(true);
    final Environment env = new Environment(envHome.getParentFile(), environmentConfig);
    if (!envHome.delete()) {
      log.debug("Can't delete temp file");
    }

    return env;
  }

  private DatabaseConfig dbConfig() {
    final DatabaseConfig dbConfig = new DatabaseConfig();
    dbConfig.setTransactional(true);
    dbConfig.setAllowCreate(true);
    dbConfig.setSortedDuplicates(true);
    return dbConfig;
  }
}
