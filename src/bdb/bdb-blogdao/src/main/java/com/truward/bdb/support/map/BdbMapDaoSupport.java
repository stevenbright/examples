package com.truward.bdb.support.map;

import com.google.protobuf.ByteString;
import com.sleepycat.je.*;
import com.truward.bdb.support.mapper.BdbEntryMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.function.Supplier;

/**
 * @author Alexander Shabanov
 */
public abstract class BdbMapDaoSupport<T> implements BdbMapDao<T> {
  private final Database database;
  private final BdbEntryMapper<T> mapper;
  private final LockMode defaultLockMode;

  public BdbMapDaoSupport(@Nonnull Database database, @Nonnull BdbEntryMapper<T> mapper) {
    this.database = database;
    this.mapper = mapper;
    this.defaultLockMode = LockMode.READ_COMMITTED;
  }

  @Nullable
  @Override
  public T get(@Nullable Transaction tx, @Nonnull ByteString key, @Nonnull Supplier<T> defaultValueSupplier) {
    final DatabaseEntry keyEntry = new DatabaseEntry(key.toByteArray());
    final DatabaseEntry valueEntry = new DatabaseEntry();
    final OperationStatus status = database.get(tx, keyEntry, valueEntry, defaultLockMode);
    if (status == OperationStatus.SUCCESS) {
      try {
        return mapper.map(keyEntry, valueEntry);
      } catch (IOException e) {
        throw new IllegalStateException(e); // TODO: another exception
      }
    }

    return defaultValueSupplier.get();
  }

  @Nonnull
  @Override
  public T get(@Nullable Transaction tx, @Nonnull ByteString key) {
    final T result = get(tx, key, () -> {
      throw new RuntimeException("There is no value corresponding to the given key"); // TODO: another exception
    });

    if (result == null) {
      // should not happen
      throw new IllegalStateException("Contract violation: null returned but never expected");
    }

    return result;
  }

  @Override
  public void put(@Nullable Transaction tx, @Nonnull ByteString key, @Nonnull T value) {
    final DatabaseEntry keyEntry = new DatabaseEntry(key.toByteArray());
    final DatabaseEntry valueEntry = toDatabaseEntry(value);

    final OperationStatus status = database.put(tx, keyEntry, valueEntry);
    if (status != OperationStatus.SUCCESS) {
      throw new IllegalStateException("Unexpected result of put");
    }
  }

  @Override
  public void delete(@Nullable Transaction tx, @Nonnull ByteString key) {
    final DatabaseEntry keyEntry = new DatabaseEntry(key.toByteArray());
    database.delete(tx, keyEntry);
  }

  //
  // Protected
  //

  @Nonnull
  protected abstract DatabaseEntry toDatabaseEntry(@Nonnull T value);
}
