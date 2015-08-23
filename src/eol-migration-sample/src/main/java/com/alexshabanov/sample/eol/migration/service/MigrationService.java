package com.alexshabanov.sample.eol.migration.service;

import com.alexshabanov.sample.eol.migration.model.BookMeta;
import com.alexshabanov.sample.eol.migration.model.NamedValue;
import com.alexshabanov.sample.eol.migration.model.SeriesPos;
import com.truward.orion.eolaire.model.EolaireModel;
import com.truward.time.UtcTime;
import com.truward.time.jdbc.UtcTimeSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

      migrate();

      log.info("Migration Completed.");
    }

    private void migrate() {
      final Map<Long, Long> genreToItem = insertNamedValues("genre",
          db.query("SELECT id, code FROM genre", new NamedValueRowMapper("code")));
      final Map<Long, Long> personToItem = insertNamedValues("person",
          db.query("SELECT id, f_name FROM author", new NamedValueRowMapper("f_name")));
      final Map<Long, Long> originToItem = insertNamedValues("flib",
          db.query("SELECT id, code FROM book_origin", new NamedValueRowMapper("code")));
      final Map<Long, Long> langToItem = insertNamedValues("language",
          db.query("SELECT id, code FROM lang_code", new NamedValueRowMapper("code")));
      final Map<Long, Long> seriesToItem = insertNamedValues("book_series",
          db.query("SELECT id, name FROM series", new NamedValueRowMapper("name")));

      final List<BookMeta> bookMetas = db.query("SELECT id, title, f_size, add_date, lang_id, origin_id " +
          "FROM book_meta ORDER BY id", new BookMetaRowMapper());
      log.info("BookMetas={}", bookMetas);

      // Get relation entity types
      final Long bookTypeId = getOrAddEntityType("book");
      final Long authorTypeId = getOrAddEntityType("author");
      final Long genreTypeId = getOrAddEntityType("genre");
      final Long originTypeId = getOrAddEntityType("origin");
      final Long languageTypeId = getOrAddEntityType("language");
      final Long seriesTypeId = getOrAddEntityType("series");

      for (final BookMeta bookMeta : bookMetas) {
        final Long itemId = addItem(bookMeta.getTitle(), bookTypeId);
        log.trace("Book {}->{}", bookMeta.getId(), itemId);

        // save origin and language relations
        insertRelation(itemId, originToItem.get(bookMeta.getOriginId()), originTypeId);
        insertRelation(itemId, langToItem.get(bookMeta.getLangId()), languageTypeId);

        // save genres relations
        final List<Long> genreIds = db.queryForList("SELECT genre_id FROM book_genre WHERE book_id=?",
            Long.class, bookMeta.getId());
        insertCodedRelations(itemId, genreIds, genreToItem, genreTypeId);

        // save authors relations
        final List<Long> authorIds = db.queryForList("SELECT author_id FROM book_author WHERE book_id=?",
            Long.class, bookMeta.getId());
        insertCodedRelations(itemId, authorIds, personToItem, authorTypeId);

        // save series relations
        final List<SeriesPos> seriesPosList = getSeriesPos(bookMeta.getId());
        Integer pos = null;
        for (SeriesPos seriesPos : seriesPosList) {
          // should be only one
          insertRelation(itemId, seriesToItem.get(seriesPos.getSeriesId()), seriesTypeId);
          pos = seriesPos.getPos() > 0 ? seriesPos.getPos() : null;
        }

        // create metadata with series position and known file size
        final EolaireModel.Metadata.Builder metadataBuilder = EolaireModel.Metadata.newBuilder();
        if (pos != null) {
          metadataBuilder.addEntries(EolaireModel.MetadataEntry.newBuilder()
              .setKey("seriesPos").setValue(EolaireModel.VariantValue.newBuilder().setIntValue(pos))
              .setType(EolaireModel.VariantType.INT32)
              .build());
        }

        metadataBuilder.addEntries(EolaireModel.MetadataEntry.newBuilder().setKey("fileSize")
            .setType(EolaireModel.VariantType.INT32)
            .setValue(EolaireModel.VariantValue.newBuilder().setIntValue(bookMeta.getFileSize())));

        insertBookProfile(itemId, 1, metadataBuilder.build());
      }
    }

    private void insertBookProfile(Long itemId, int flag, EolaireModel.Metadata metadata) {
      final UtcTime now = UtcTime.now();
      db.update("INSERT INTO item_profile (item_id, description, date_created, date_updated, flags, metadata) " +
          "VALUES (?, ?, ?, ?, ?, ?)", itemId, null, now.asCalendar(), now.asCalendar(), flag, metadata);
    }

    private List<SeriesPos> getSeriesPos(Long bookId) {
      return db.query("SELECT series_id, pos FROM book_series WHERE book_id=?",
          (rs, i) -> new SeriesPos(rs.getLong("series_id"), rs.getInt("pos")), bookId);
    }

    private void insertCodedRelations(Long lhs, List<Long> codedRhsList, Map<Long, Long> map, Long typeId) {
      for (final Long codedRhs : codedRhsList) {
        insertRelation(lhs, map.get(codedRhs), typeId);
      }
    }

    private void insertRelation(Long lhs, Long rhs, Long typeId) {
      assert lhs != null && rhs != null && typeId != null;
      db.update("INSERT INTO item_relation (lhs, rhs, type_id) VALUES (?, ?, ?)", lhs, rhs, typeId);
    }

    private Map<Long, Long> insertNamedValues(String typeName, List<NamedValue> values) {
      final Long entityTypeId = getOrAddEntityType(typeName);
      final Map<Long, Long> result = new HashMap<>(values.size() * 2);

      for (final NamedValue value : values) {
        final Long itemId = getOrAddItem(value.getName(), entityTypeId);
        result.put(value.getId(), itemId);
      }

      return result;
    }

    private Long addItem(String itemName, Long itemTypeId) {
      final Long id = getNextItemId();
      db.update("INSERT INTO item (id, name, type_id) VALUES (?, ?, ?)", id, itemName, itemTypeId);
      return id;
    }

    private Long getOrAddItem(String itemName, Long itemTypeId) {
      final List<Long> existingIds = db.queryForList("SELECT id FROM item WHERE name=? AND type_id=?",
          Long.class, itemName, itemTypeId);
      if (!existingIds.isEmpty()) {
        assert existingIds.size() == 1;
        return existingIds.get(0);
      }

      return addItem(itemName, itemTypeId);
    }

    private Long getOrAddEntityType(String entityName) {
      final List<Long> existingIds = db.queryForList("SELECT id FROM entity_type WHERE name=?", Long.class, entityName);
      if (!existingIds.isEmpty()) {
        assert existingIds.size() == 1;
        return existingIds.get(0);
      }

      final Long id = getNextEntityTypeId();
      db.update("INSERT INTO entity_type (id, name) VALUES (?, ?)", id, entityName);
      return id;
    }

    private Long getNextItemId() {
      return db.queryForObject("SELECT seq_item.nextval", Long.class);
    }

    private Long getNextEntityTypeId() {
      return db.queryForObject("SELECT seq_entity_type.nextval", Long.class);
    }
  }

  //
  // Private
  //

  private static final class NamedValueRowMapper implements RowMapper<NamedValue> {
    private final String name;

    public NamedValueRowMapper(String name) {
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
      return new BookMeta(rs.getLong("id"), rs.getString("title"), rs.getInt("f_size"),
          UtcTimeSqlUtil.getNullableUtcTime(rs, "add_date", UtcTime.now()),
          rs.getLong("lang_id"), rs.getLong("origin_id"));
    }
  }
}
