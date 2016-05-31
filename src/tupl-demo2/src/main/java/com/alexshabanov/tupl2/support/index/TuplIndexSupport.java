package com.alexshabanov.tupl2.support.index;

import com.alexshabanov.tupl2.support.exception.InternalErrorDaoException;
import com.alexshabanov.tupl2.support.TuplDatabaseSupport;
import org.cojen.tupl.Index;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * Support mixin for Tupl operations.
 */
public interface TuplIndexSupport extends TuplDatabaseSupport {

  default <T> T withIndex(@Nonnull String indexName,
                          @Nonnull TuplIndexOperationCallback<T> callback) {
    try (final Index index = getDatabase().openIndex(indexName)) {
      return callback.call(index);
    } catch (IOException e) {
      throw new InternalErrorDaoException(e);
    }
  }
}
