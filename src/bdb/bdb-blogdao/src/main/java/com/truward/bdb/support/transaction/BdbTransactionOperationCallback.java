package com.truward.bdb.support.transaction;

import com.sleepycat.je.Transaction;

import java.io.IOException;

/**
 * @author Alexander Shabanov
 */
public interface BdbTransactionOperationCallback<T> {
  T call(Transaction tx) throws IOException;
}
