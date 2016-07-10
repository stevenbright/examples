package com.truward.bdb.support.map;

import com.google.protobuf.ByteString;
import com.sleepycat.je.Transaction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * @author Alexander Shabanov
 */
public interface BdbMapDao<T> {

  @Nullable
  T get(@Nullable Transaction tx, @Nonnull ByteString key, @Nonnull Supplier<T> defaultValueSupplier);

  @Nonnull
  T get(@Nullable Transaction tx, @Nonnull ByteString key);

  void put(@Nullable Transaction tx, @Nonnull ByteString key, @Nonnull T value);

  void delete(@Nullable Transaction tx, @Nonnull ByteString key);
}
