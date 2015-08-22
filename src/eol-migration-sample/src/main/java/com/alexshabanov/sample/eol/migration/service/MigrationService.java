package com.alexshabanov.sample.eol.migration.service;

import com.alexshabanov.sample.eol.migration.model.NamedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class MigrationService {
  public interface Contract {
    void runMigration();
  }

  public static final class Impl implements Contract {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final JdbcOperations db;

    public Impl(JdbcOperations db) {
      this.db = db;
    }

    @Override
    @Transactional
    public void runMigration() {
      final String n = new NamedValue(1L, "dfs").getName();
      log.info("About to run migration...");

      try {
        final int i = db.queryForObject("SELECT COUNT(0) FROM item", Integer.class);
        log.info("Got i={}", i);
      } catch (DataAccessException ignored) {
        log.warn("There is no items table, schema is invalid, returning");
        return;
      }

      // run other diagnostics queries
      db.queryForObject("SELECT COUNT(0) FROM entity_type", Integer.class);

      // booklib tables
      db.queryForObject("SELECT COUNT(0) FROM genre", Integer.class);
      db.queryForObject("SELECT COUNT(0) FROM author", Integer.class);
      db.queryForObject("SELECT COUNT(0) FROM entity_type", Integer.class);
      db.queryForObject("SELECT COUNT(0) FROM book_meta", Integer.class);

      //db.query("")

      // TODO: remove, once components will play nicely together
      if (db.queryForObject("SELECT COUNT(0) FROM book_meta", Integer.class) != 1) {
        throw new IllegalStateException("Forcing rollback in a hacky way");
      }
    }
  }

  //
  // Private
  //

  private static final class GenreRowMapper implements RowMapper<NamedValue> {

    @Override
    public NamedValue mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new NamedValue(rs.getLong("id"), rs.getString("name"));
    }
  }
}
