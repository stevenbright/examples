package com.alexshabanov.tupl2.dao;

import com.alexshabanov.tupl2.model.support.Author;
import com.alexshabanov.tupl2.model.support.Book;
import com.alexshabanov.tupl2.model.support.BookUpdate;
import com.alexshabanov.tupl2.model.support.Genre;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Book DAO contract.
 */
public interface BookDao {

  @Nonnull
  Author getAuthor(@Nonnull String id);

  @Nonnull
  String saveAuthor(@Nonnull Author author);

  @Nonnull
  List<Author> getAuthors(@Nullable String startId, int limit);

  @Nonnull
  Genre getGenre(@Nonnull String id);

  @Nonnull
  String saveGenre(@Nonnull Genre genre);

  @Nonnull
  Book getBook(@Nonnull String id);

  @Nonnull
  String saveBook(@Nonnull BookUpdate bookUpdate);
}
