package com.truward.bdb.support.protobuf;

import com.google.protobuf.MessageLite;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.truward.bdb.support.map.BdbMapDaoSupport;
import com.truward.bdb.support.map.MapDaoConfig;
import com.truward.bdb.support.mapper.BdbEntryMapper;

import javax.annotation.Nonnull;

/**
 * @author Alexander Shabanov
 */
public class ProtobufBdbMapDaoSupport<T extends MessageLite> extends BdbMapDaoSupport<T> {

  public ProtobufBdbMapDaoSupport(@Nonnull MapDaoConfig<T> config) {
    super(config);
  }

  @Nonnull
  @Override
  protected final DatabaseEntry toDatabaseEntry(@Nonnull T value) {
    return new DatabaseEntry(value.toByteArray());
  }
}
