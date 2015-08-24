package com.alexshabanov.sample.eol.migration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MigrationService {
  public interface Contract {
    void runMigration();
  }

  public static final class Impl implements Contract {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DataTransferService.Contract dataTransferService;

    public Impl(DataTransferService.Contract dataTransferService) {
      this.dataTransferService = dataTransferService;
    }

    @Override
    public void runMigration() {
      log.info("About to run migration...");

      if (!dataTransferService.prepare()) {
        return;
      }

      for (Long startId = null;;) {
        log.info("Moving next chunk of data");
        startId = dataTransferService.transferNext(startId);
        if (startId == null) {
          break;
        }
      }

      dataTransferService.complete();

      log.info("Migration Completed.");
    }
  }
}
