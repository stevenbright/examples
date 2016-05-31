package com.alexshabanov.tupl2.support;

import org.cojen.tupl.Database;

import javax.annotation.Nonnull;

/**
 * An abstraction over entity that holds a reference to the Tupl database.
 */
public interface TuplDatabaseSupport {

  @Nonnull
  Database getDatabase();
}
