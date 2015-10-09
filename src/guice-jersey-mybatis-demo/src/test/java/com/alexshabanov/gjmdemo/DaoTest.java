package com.alexshabanov.gjmdemo;

import com.alexshabanov.gjmdemo.logic.model.User;
import com.alexshabanov.gjmdemo.logic.service.mapper.UserMapper;
import com.alexshabanov.gjmdemo.logic.service.helper.DbInitializer;
import com.alexshabanov.gjmdemo.server.config.DaoModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public final class DaoTest {

  private UserMapper userMapper;

  @Before
  public void init() {
    final Properties myBatisProperties = new Properties();
    myBatisProperties.setProperty("mybatis.environment.id", "test");
    // TODO: reuse the same schema!
    myBatisProperties.setProperty("JDBC.schema", "DaoTest-" + Long.toHexString(ThreadLocalRandom.current().nextLong()));
    myBatisProperties.setProperty("JDBC.username", "sa");
    myBatisProperties.setProperty("JDBC.password", "");
    myBatisProperties.setProperty("JDBC.autoCommit", "false");

    myBatisProperties.setProperty("gjmDemo.dao.initScripts", "gjmDemo/sql/user-schema.sql,gjmDemo/sql/user-fixture.sql");

    final Injector injector = Guice.createInjector(new DaoModule(myBatisProperties));

    injector.getInstance(DbInitializer.class).initialize();
    this.userMapper = injector.getInstance(UserMapper.class);
  }

  @Test
  public void shouldGetUserId() {
    final long aliceId = userMapper.getUserIdByUsername("alice");
    assertEquals(1000L, aliceId);
  }

  @Test
  public void shouldGetUsers() {
    assertEquals(4, userMapper.getUsers().size());
  }

  @Test
  public void shouldGetUserById() {
    final User user = new User();
    user.setId(1000L);
    user.setUsername("alice");
    user.setAge(18);
    user.setNote("SysAdmin");
    assertEquals(user, userMapper.getUser(1000L));
  }
}
