package com.alexshabanov.tupl2.support.transaction;

import com.alexshabanov.tupl2.support.exception.InternalErrorDaoException;
import com.alexshabanov.tupl2.support.TuplDatabaseSupport;
import org.cojen.tupl.Transaction;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * Mixin that provides support for Tupl transactions.
 */
public interface TuplTransactionSupport extends TuplDatabaseSupport {

  default <T> T withTransaction(@Nonnull TuplTransactionOperationCallback<T> callback) {
    final Transaction tx = getDatabase().newTransaction();
    try {
      try {
        final T result = callback.call(tx);
        tx.commit();
        return result;
      } finally {
        tx.exit();
      }
    } catch (IOException e) {
      throw new InternalErrorDaoException(e);
    }
  }
}
