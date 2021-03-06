package com.truward.bdb.support.transaction;

import com.sleepycat.je.Transaction;

import javax.annotation.Nonnull;

/**
 * @author Alexander Shabanov
 */
public interface BdbTransactionOperationCallback<T> {
  T call(@Nonnull Transaction tx);
}
