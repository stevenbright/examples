package com.alexshabanov.bdbsample.dao;

import com.alexshabanov.bdbsample.model.Blog;
import com.sleepycat.je.DatabaseEntry;
import com.truward.bdb.support.mapper.BdbEntryMapper;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * @author Alexander Shabanov
 */
public final class Mappers {

  public static final BdbEntryMapper<Blog.BlogEntry> BLOG_ENTRY_MAPPER = new BdbEntryMapper<Blog.BlogEntry>() {
    @Nonnull
    @Override
    public Blog.BlogEntry map(@Nonnull DatabaseEntry key, @Nonnull DatabaseEntry value) throws IOException {
      return Blog.BlogEntry.parseFrom(value.getData());
    }
  };

  private Mappers() {} // hidden
}
