package com.truward.bdb.support.map;

import com.google.common.collect.ImmutableList;
import com.google.protobuf.ByteString;
import com.sleepycat.je.*;
import com.truward.bdb.support.mapper.BdbEntryMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author Alexander Shabanov
 */
public abstract class BdbMapDaoSupport<T> implements BdbMapDao<T> {
  private final Database database;
  private final BdbEntryMapper<T> mapper;
  private final LockMode lockMode;

  public BdbMapDaoSupport(@Nonnull MapDaoConfig<T> config) {
    this.database = config.getDatabase();
    this.mapper = config.getMapper();
    this.lockMode = config.getLockMode();
  }

  @Nullable
  @Override
  public T get(@Nullable Transaction tx, @Nonnull ByteString key, @Nonnull Supplier<T> defaultValueSupplier) {
    final DatabaseEntry keyEntry = new DatabaseEntry(key.toByteArray());
    final DatabaseEntry valueEntry = new DatabaseEntry();
    final OperationStatus status = database.get(tx, keyEntry, valueEntry, lockMode);
    if (status == OperationStatus.SUCCESS) {
      try {
        return mapper.map(keyEntry, valueEntry);
      } catch (IOException e) {
        throw new IllegalStateException("Mapping error", e); // TODO: another exception
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

  @Nonnull
  @Override
  public List<Map.Entry<ByteString, T>> getEntries(@Nullable Transaction tx, int offset, int limit) {
    final DatabaseEntry outKey = new DatabaseEntry();
    final DatabaseEntry outValue = new DatabaseEntry();
    final LockMode lockMode = (this.lockMode == LockMode.READ_COMMITTED ? LockMode.DEFAULT : this.lockMode);
    try (final Cursor cursor = database.openCursor(tx, null)) {
      if (OperationStatus.SUCCESS != cursor.getFirst(outKey, outValue, lockMode)) {
        return Collections.emptyList();
      }

      final List<Map.Entry<ByteString, T>> result = new ArrayList<>();
      int pos = 0;
      do {
        if (pos < offset) {
          continue;
        }

        if ((offset - pos) >= limit) {
          break;
        }

        final ByteString key = ByteString.copyFrom(outKey.getData(), outKey.getOffset(), outKey.getSize());
        final T value = mapper.map(outKey, outValue);
        result.add(new AbstractMap.SimpleImmutableEntry<>(key, value));
        ++pos;
      } while (OperationStatus.SUCCESS == cursor.getNext(outKey, outValue, lockMode));
      return ImmutableList.copyOf(result);
    } catch (IOException e) {
      throw new IllegalStateException("Mapping error", e); // TODO: another exception if value has not been properly mapped
    }
  }

  @Override
  public void put(@Nullable Transaction tx, @Nonnull ByteString key, @Nonnull T value) {
    final DatabaseEntry keyEntry = new DatabaseEntry(key.toByteArray());
    final DatabaseEntry valueEntry = toDatabaseEntry(value);

    final OperationStatus status = database.put(tx, keyEntry, valueEntry);
    if (status != OperationStatus.SUCCESS) {
      throw new IllegalStateException("Unexpected put result=" + status);
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
