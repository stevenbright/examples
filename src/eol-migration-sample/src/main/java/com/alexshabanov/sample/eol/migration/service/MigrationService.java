package com.alexshabanov.sample.eol.migration.service;

import com.alexshabanov.sample.eol.migration.model.BookMeta;
import com.alexshabanov.sample.eol.migration.model.NamedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
      log.info("About to run migration...");

      try {
        final int count = db.queryForObject("SELECT COUNT(0) FROM item", Integer.class);
        log.info("Number of existing items: {}", count);
      } catch (DataAccessException ignored) {
        log.warn("There is no items table, schema is invalid, returning");
        return;
      }

      // run other diagnostics queries
      db.queryForObject("SELECT COUNT(0) FROM entity_type", Integer.class);
      db.queryForObject("SELECT COUNT(0) FROM entity_type", Integer.class);

      // booklib tables

      final List<NamedValue> genres = db.query("SELECT id, code FROM genre", new GenreRowMapper("code"));
      log.info("Genres={}", genres);

      final List<NamedValue> origins = db.query("SELECT id, code FROM book_origin", new GenreRowMapper("code"));
      log.info("BookOrigins={}", origins);

      final List<NamedValue> authors = db.query("SELECT id, f_name FROM author", new GenreRowMapper("f_name"));
      log.info("Authors={}", authors);

      final List<BookMeta> bookMetas = db.query("SELECT id, title, f_size, add_date, lang_id, origin_id " +
          "FROM book_meta ORDER BY id", new BookMetaRowMapper());
      log.info("BookMetas={}", bookMetas);

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
    private final String name;

    public GenreRowMapper(String name) {
      this.name = name;
    }

    @Override
    public NamedValue mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new NamedValue(rs.getLong("id"), rs.getString(name));
    }
  }

  private static final class BookMetaRowMapper implements RowMapper<BookMeta> {

    @Override
    public BookMeta mapRow(ResultSet rs, int rowNum) throws SQLException {
      final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
      final long unixDate = rs.getTimestamp("add_date", cal).getTime();
      final Date date = new Date(unixDate);
      return new BookMeta(rs.getLong("id"), rs.getString("title"), rs.getInt("f_size"), date,
          rs.getLong("lang_id"), rs.getLong("origin_id"));
    }
  }
}
