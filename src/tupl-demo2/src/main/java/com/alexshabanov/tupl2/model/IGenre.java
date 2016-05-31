package com.alexshabanov.tupl2.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents genre associated with a book.
 * TODO: complete.
 */
public interface IGenre {
  @Nullable String getId();
  @Nonnull String getShortName();
  @Nonnull String getLongName();
  @Nonnull String getDescription();
}
