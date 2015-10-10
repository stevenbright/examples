package com.alexshabanov.gjmdemo.server.config;

import com.alexshabanov.gjmdemo.logic.service.helper.DbInitializer;
import com.alexshabanov.gjmdemo.logic.service.mapper.UserMapper;
import com.google.inject.name.Names;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;

import java.util.Objects;
import java.util.Properties;

/**
 * @author Alexander Shabanov
 */
public final class DaoModule extends MyBatisModule {
  private final Properties daoProperties;

  public DaoModule(Properties daoProperties) {
    this.daoProperties = Objects.requireNonNull(daoProperties);
  }

  @Override
  protected void initialize() {
    bindDataSourceProviderType(PooledDataSourceProvider.class);
    bindTransactionFactoryType(JdbcTransactionFactory.class);
    addMapperClass(UserMapper.class);

    Names.bindProperties(binder(), daoProperties);

    bind(DbInitializer.class);
  }
}
