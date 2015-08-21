package com.alexshabanov.sample.eol.migration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;

public final class MigrationService {
  public interface Contract {
    void runMigration();
  }

  public static final class Impl implements Contract {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final JdbcOperations jdbcOperations;

    public Impl(JdbcOperations jdbcOperations) {
      this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void runMigration() {
      log.info("About to run migration...");

      final int i = jdbcOperations.queryForObject("SELECT 1", Integer.class);
      log.info("Got i={}", i);
    }
  }
}
