package com.alexshabanov.bdbsample;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sleepycat.je.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.File;

public final class App implements Runnable {

  private final Logger log = LoggerFactory.getLogger(getClass());

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
    final File envHome = File.createTempFile("TestDB-", "-bdb");
    final EnvironmentConfig environmentConfig = new EnvironmentConfig();
    environmentConfig.setAllowCreate(true);
    environmentConfig.setTransactional(true);
    final Environment env = new Environment(envHome.getParentFile(), environmentConfig);
    if (!envHome.delete()) {
      log.debug("Can't delete temp file");
    }

    final DatabaseConfig dbConfig = new DatabaseConfig();
    dbConfig.setTransactional(true);
    dbConfig.setAllowCreate(true);
    dbConfig.setSortedDuplicates(true);
    final Database db = env.openDatabase(null, "mydatabase", dbConfig);

    final DatabaseEntry entry = new DatabaseEntry("key".getBytes());
    final DatabaseEntry value = new DatabaseEntry("value".getBytes());
    db.put(null, entry, value);

    final DatabaseEntry out = new DatabaseEntry();
    db.get(null, entry, out, LockMode.DEFAULT);

    log.info("out={}", toHexString(out));
  }

  private static String toHexString(DatabaseEntry entry) {
    return (entry != null && entry.getData() != null) ? ("0x" + DatatypeConverter.printHexBinary(entry.getData())) :
        "<null>";
  }

  // app configuration
  public static final class AppModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(App.class);
    }
  }


  public static void main(String[] args) {
    final Injector injector = Guice.createInjector(new AppModule());
    final Runnable runnableApp = injector.getInstance(App.class);
    runnableApp.run();
  }
}
