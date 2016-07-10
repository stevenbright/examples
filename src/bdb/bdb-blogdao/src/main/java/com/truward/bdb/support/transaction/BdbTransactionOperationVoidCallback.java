package com.truward.bdb.support.transaction;

import com.sleepycat.je.Transaction;

import javax.annotation.Nonnull;

/**
 * @author Alexander Shabanov
 */
public interface BdbTransactionOperationVoidCallback {
  void call(@Nonnull Transaction tx);
}
