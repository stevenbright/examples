package com.alexshabanov.tupl2.support.transaction;

import org.cojen.tupl.Transaction;

import java.io.IOException;

public interface TuplTransactionOperationCallback<T> {
  T call(Transaction tx) throws IOException;
}
