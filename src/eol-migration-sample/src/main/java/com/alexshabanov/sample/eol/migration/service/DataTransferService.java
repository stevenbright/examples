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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Shabanov
 */
public final class DataTransferService {
  private DataTransferService() {}

  public interface Contract {
    boolean prepare();

    Long transferNext(Long startId);

    void complete();
  }

  @Transactional
  public static final class Impl implements Contract {
    private static final int BOOK_TRANSFER_LIMIT = 500;

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final JdbcOperations db;

    private Long bookTypeId;
    private Long authorTypeId;
    private Long genreTypeId;
    private Long originTypeId;
    private Long languageTypeId;
    private Long seriesTypeId;

    private Map<Long, Long> genreToItem;
    private Map<Long, Long> personToItem;
    private Map<Long, Long> originToItem;
    private Map<Long, Long> langToItem;
    private Map<Long, Long> seriesToItem;

    public Impl(JdbcOperations db) {
      this.db = db;
    }

    @Override
    public boolean prepare() {
      log.info("About to run migration...");

      try {
        final int count = db.queryForObject("SELECT COUNT(0) FROM item", Integer.class);
        log.info("Number of existing items: {}", count);
      } catch (DataAccessException ignored) {
        log.warn("There is no items table, schema is invalid, returning");
        return false;
      }

      // Get relation entity types
      this.bookTypeId = getOrAddEntityType("book");
      this.authorTypeId = getOrAddEntityType("author");
      this.genreTypeId = getOrAddEntityType("genre");
      this.originTypeId = getOrAddEntityType("origin");
      this.languageTypeId = getOrAddEntityType("language");
      this.seriesTypeId = getOrAddEntityType("series");

      // Create ID mappings
      this.genreToItem = insertNamedValues("genre", db.query("SELECT id, code FROM genre", new NamedValueRowMapper("code")));
      this.personToItem = insertNamedValues("person", db.query("SELECT id, f_name FROM author", new NamedValueRowMapper("f_name")));
      this.originToItem = insertNamedValues("flib", db.query("SELECT id, code FROM book_origin", new NamedValueRowMapper("code")));
      this.langToItem = insertNamedValues("language", db.query("SELECT id, code FROM lang_code", new NamedValueRowMapper("code")));
      this.seriesToItem = insertNamedValues("book_series", db.query("SELECT id, name FROM series", new NamedValueRowMapper("name")));

      return true;
    }

    @Override
    public Long transferNext(Long startId) {
      final int limit = BOOK_TRANSFER_LIMIT;
      final List<BookMeta> bookMetas = db.query("SELECT id, title, f_size, add_date, lang_id, origin_id " +
          "FROM book_meta WHERE ((? IS NULL) OR (id > ?)) ORDER BY id LIMIT ?", new BookMetaRowMapper(),
          startId, startId, limit);
      log.info("BookMetas={}", bookMetas);

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

      if (limit > bookMetas.size()) {
        return null;
      }

      return bookMetas.get(bookMetas.size() - 1).getId(); // last ID
    }

    @Override
    public void complete() {
      final int origBookCount = db.queryForObject("SELECT COUNT(0) FROM book_meta", Integer.class);
      final int actualBookCount = db.queryForObject("SELECT COUNT(0) FROM item WHERE type_id=?",
          Integer.class, bookTypeId);

      assert origBookCount == actualBookCount;

      db.update("DROP TABLE book_genre");
      db.update("DROP TABLE book_author");
      db.update("DROP TABLE book_series");
      db.update("DROP TABLE series");

      db.update("DROP TABLE book_meta");

      db.update("DROP TABLE book_origin");
      db.update("DROP TABLE lang_code");
      db.update("DROP TABLE genre");
      db.update("DROP TABLE author");
    }

    //
    // Private
    //

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
}
