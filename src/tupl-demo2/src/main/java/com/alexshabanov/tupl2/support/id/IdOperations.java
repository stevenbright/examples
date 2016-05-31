package com.alexshabanov.tupl2.support.id;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Abstraction, over string id generation.
 */
public interface IdOperations {

  @Nonnull
  String generateId();

  @Nonnull
  byte[] fromId(@Nonnull String id);

  @Nonnull
  byte[] fromNullableId(@Nullable String id);

  @Nonnull
  String toId(@Nonnull byte[] arr);
}
