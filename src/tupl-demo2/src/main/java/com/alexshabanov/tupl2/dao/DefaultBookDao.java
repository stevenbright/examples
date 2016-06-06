package com.alexshabanov.tupl2.dao;

import com.alexshabanov.tupl2.Bookdb;
import com.alexshabanov.tupl2.util.BookdbUtil;
import com.alexshabanov.tupl2.model.support.Author;
import com.alexshabanov.tupl2.model.support.Book;
import com.alexshabanov.tupl2.model.support.BookUpdate;
import com.alexshabanov.tupl2.model.support.Genre;
import com.truward.tupl.support.id.IdSupport;
import com.truward.tupl.support.load.ByteArrayResultMapper;
import com.truward.tupl.support.load.TuplLoadSupport;
import com.truward.tupl.support.transaction.TuplTransactionSupport;
import com.truward.tupl.support.update.TuplUpdateSupport;
import org.cojen.tupl.Database;
import org.cojen.tupl.Ordering;
import org.cojen.tupl.Transaction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link BookDao}.
 */
public final class DefaultBookDao implements BookDao, IdSupport,
    TuplTransactionSupport, TuplUpdateSupport, TuplLoadSupport {

  private static final String AUTHOR_INDEX = "Author";
  private static final String GENRE_INDEX = "Genre";
  private static final String BOOK_INDEX = "Book";

  private static final ByteArrayResultMapper<Author> AUTHOR_MAPPER = (id, result) ->
      BookdbUtil.fromAuthor(id, Bookdb.Author.parseFrom(result));

  private final Database db;

  public DefaultBookDao(@Nonnull Database db) {
    this.db = Objects.requireNonNull(db, "db");
  }

  @Nonnull
  @Override
  public Database getDatabase() {
    return db;
  }

  @Nonnull
  @Override
  public Author getAuthor(@Nonnull String id) {
    return withTransaction(tx -> getAuthor(tx, id));
  }

  @Nonnull
  @Override
  public String saveAuthor(@Nonnull Author author) {
    return withTransaction(tx -> saveAuthor(tx, author));
  }

  @Nonnull
  @Override
  public List<Author> getAuthors(@Nullable String startId, int limit) {
    return withTransaction(tx -> getAuthors(tx, startId, limit));
  }

  @Nonnull
  @Override
  public Genre getGenre(@Nonnull String id) {
    return withTransaction(tx -> getGenre(tx, id));
  }

  @Nonnull
  @Override
  public String saveGenre(@Nonnull Genre genre) {
    return withTransaction(tx -> saveGenre(tx, genre));
  }

  @Nonnull
  @Override
  public Book getBook(@Nonnull String id) {
    return withTransaction(tx -> getBook(tx, id));
  }

  @Nonnull
  @Override
  public String saveBook(@Nonnull BookUpdate bookUpdate) {
    return withTransaction(tx -> saveBook(tx, bookUpdate));
  }

  //
  // Private
  //

  @Nonnull
  private Author getAuthor(@Nonnull Transaction tx, @Nonnull String id) {
    return loadObject(tx, AUTHOR_INDEX, id, AUTHOR_MAPPER);
  }

  @Nonnull
  private String saveAuthor(@Nonnull Transaction tx, @Nonnull Author author) {
    return updateObject(tx, AUTHOR_INDEX, author.getId(), BookdbUtil.toAuthor(author).toByteArray());
  }

  @Nonnull
  private List<Author> getAuthors(@Nonnull Transaction tx, @Nullable String startId, int limit) {
    return loadInOrder(tx, AUTHOR_INDEX, Ordering.ASCENDING, startId, limit, AUTHOR_MAPPER);
  }

  @Nonnull
  private Genre getGenre(@Nonnull Transaction tx, @Nonnull String id) {
    return loadObject(tx, GENRE_INDEX, id, (unused, result) -> BookdbUtil.fromGenre(id, Bookdb.Genre.parseFrom(result)));
  }

  @Nonnull
  public String saveGenre(@Nonnull Transaction tx, @Nonnull Genre genre) {
    return updateObject(tx, GENRE_INDEX, genre.getId(), BookdbUtil.toGenre(genre).toByteArray());
  }

  @Nonnull
  private Book getBook(@Nonnull Transaction tx, @Nonnull String id) {
    return loadObject(tx, BOOK_INDEX, id, (unused, result) -> {
      final Bookdb.Book value = Bookdb.Book.parseFrom(result);
      final Book r = new Book();
      r.setId(id);
      r.setTitle(value.getTitle());
      r.setIsbn(value.getIsbn());
      r.setPages(value.getPages());
      r.setAuthors(value.getAuthorIdsList()
          .stream()
          .map(authorId -> getAuthor(tx, authorId))
          .collect(Collectors.toList()));
      r.setGenres(value.getGenreIdsList()
          .stream()
          .map(genreId -> getGenre(tx, genreId))
          .collect(Collectors.toList()));
      return r;
    });
  }

  @Nonnull
  private String saveBook(@Nonnull Transaction tx, @Nonnull BookUpdate bookUpdate) {
    return updateObject(tx, BOOK_INDEX, bookUpdate.getId(),
        BookdbUtil.toBook(bookUpdate).toByteArray());
  }
}
