package com.alexshabanov.tupl2.util;

import com.alexshabanov.tupl2.Bookdb;
import com.alexshabanov.tupl2.model.support.Author;
import com.alexshabanov.tupl2.model.support.BookUpdate;
import com.alexshabanov.tupl2.model.support.Genre;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Data mapping utilities.
 */
public final class BookdbUtil {
  private BookdbUtil() {} // Hidden

  @Nonnull
  public static Bookdb.Author to(@Nonnull Author value) {
    return Bookdb.Author.newBuilder()
        .setName(value.getName())
        .setDescription(value.getDescription())
        .build();
  }

  @Nonnull
  public static Author from(@Nullable String id, @Nonnull Bookdb.Author value) {
    final Author r = new Author();
    r.setId(id);
    r.setName(value.getName());
    r.setDescription(value.getDescription());
    return r;
  }

  @Nonnull
  public static Bookdb.Genre to(Genre value) {
    return Bookdb.Genre.newBuilder()
        .setShortName(value.getShortName())
        .setLongName(value.getLongName())
        .setDescription(value.getDescription())
        .build();
  }

  @Nonnull
  public static Genre from(@Nullable String id, @Nonnull Bookdb.Genre value) {
    final Genre r = new Genre();
    r.setId(id);
    r.setShortName(value.getShortName());
    r.setLongName(value.getLongName());
    r.setDescription(value.getDescription());
    return r;
  }

  @Nonnull
  public static Bookdb.Book to(@Nonnull BookUpdate value) {
    return Bookdb.Book.newBuilder()
        .setTitle(value.getTitle())
        .setIsbn(value.getIsbn())
        .setPages(value.getPages())
        .addAllAuthorIds(value.getAuthors())
        .addAllGenreIds(value.getGenres())
        .build();
  }
}
