package com.truward.bdb.support.protobuf;

import com.google.protobuf.MessageLite;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.truward.bdb.support.map.BdbMapDaoSupport;
import com.truward.bdb.support.mapper.BdbEntryMapper;

import javax.annotation.Nonnull;

/**
 * @author Alexander Shabanov
 */
public class ProtobufBdbMapDaoSupport<T extends MessageLite> extends BdbMapDaoSupport<T> {

  public ProtobufBdbMapDaoSupport(@Nonnull Database database, @Nonnull BdbEntryMapper<T> mapper, @Nonnull LockMode lockMode) {
    super(database, mapper, lockMode);
  }

  public ProtobufBdbMapDaoSupport(@Nonnull Database database, @Nonnull BdbEntryMapper<T> mapper) {
    super(database, mapper);
  }

  @Nonnull
  @Override
  protected final DatabaseEntry toDatabaseEntry(@Nonnull T value) {
    return new DatabaseEntry(value.toByteArray());
  }
}
