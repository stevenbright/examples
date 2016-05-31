package com.alexshabanov.tupl2.support.load;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * Result mapper.
 */
public interface ByteArrayResultMapper<T> {

  @Nonnull
  T map(@Nonnull String id, @Nonnull byte[] objectContents) throws IOException;
}
