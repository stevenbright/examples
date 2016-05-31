package com.alexshabanov.tupl2.support.update;

import com.alexshabanov.tupl2.support.exception.KeyNotFoundException;
import com.alexshabanov.tupl2.support.index.TuplIndexSupport;
import com.alexshabanov.tupl2.support.id.IdOperations;
import org.cojen.tupl.Transaction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Mixin that provides support for insert operation for Tupl DB.
 */
public interface TuplUpdateSupport extends TuplIndexSupport, IdOperations {

  @Nonnull
  default String updateObject(@Nonnull Transaction tx,
                              @Nonnull String indexName,
                              @Nullable String id,
                              @Nonnull byte[] objectContents) {
    return withIndex(indexName, index -> {
      String resultId = id;
      if (resultId != null) {
        if (!index.replace(tx, fromId(resultId), objectContents)) {
          throw new KeyNotFoundException(id);
        }
      } else {
        boolean inserted;
        do {
          resultId = generateId();
          inserted = index.insert(tx, fromId(resultId), objectContents);
        } while (!inserted); // repeat until new key is created
      }
      return resultId;
    });
  }
}
