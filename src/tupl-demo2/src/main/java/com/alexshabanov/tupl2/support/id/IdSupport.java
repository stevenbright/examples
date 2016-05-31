package com.alexshabanov.tupl2.support.id;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Support mixin for ID operations.
 * Every symbol in the generated ID can take 32 possible values, though it is not base32.
 */
public interface IdSupport extends IdOperations {

  String ID_CHARS = "01234567890ABCDEFGHKLMNPQRSTWXYZ";
  int ID_SIZE = 12;
  byte[] EMPTY_ID = new byte[0];

  @Nonnull
  default Random getRandom() {
    return ThreadLocalRandom.current();
  }

  @Nonnull
  @Override
  default String generateId() {
    final char[] buf = new char[ID_SIZE];
    for (int i = 0; i < buf.length; ++i) {
      buf[i] = ID_CHARS.charAt(getRandom().nextInt(ID_CHARS.length()));
    }
    return new String(buf);
  }

  @Nonnull
  @Override
  default byte[] fromId(@Nonnull String id) {
    return id.getBytes(StandardCharsets.UTF_8);
  }

  @Nonnull
  @Override
  default byte[] fromNullableId(@Nullable String id) {
    return id != null ? fromId(id) : EMPTY_ID;
  }

  @Nonnull
  @Override
  default String toId(@Nonnull byte[] arr) {
    return new String(arr, StandardCharsets.UTF_8);
  }
}
