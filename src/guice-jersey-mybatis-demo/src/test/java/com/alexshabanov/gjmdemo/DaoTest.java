package com.alexshabanov.gjmdemo;

import com.alexshabanov.gjmdemo.logic.model.User;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;
import org.mybatis.guice.transactional.Transactional;

import javax.inject.Inject;
import java.util.Properties;

@Ignore
public class DaoTest {

  @Test
  public void shouldPass() {
    final Properties myBatisProperties = new Properties();
    myBatisProperties.setProperty("mybatis.environment.id", "test");
    myBatisProperties.setProperty("JDBC.schema", "mybatis-guice_TEST");
    myBatisProperties.setProperty("derby.create", "true");
    myBatisProperties.setProperty("JDBC.username", "sa");
    myBatisProperties.setProperty("JDBC.password", "");
    myBatisProperties.setProperty("JDBC.autoCommit", "false");

    final Injector injector = Guice.createInjector(
        new MyBatisModule() {

          @Override
          protected void initialize() {
            install(JdbcHelper.HSQLDB_Embedded);

            bindDataSourceProviderType(PooledDataSourceProvider.class);
            bindTransactionFactoryType(JdbcTransactionFactory.class);
            addMapperClass(UserMapper.class);

            Names.bindProperties(binder(), myBatisProperties);
            bind(FooService.class).to(FooServiceMapperImpl.class);
          }

        }
    );


  }

  //
  // Private
  //

  public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{userId}")
    User getUser(@Param("userId") String userId);
  }

  public interface FooService {
    User doSomeBusinessStuff(String userId);
  }

  public static class FooServiceMapperImpl implements FooService {

    @Inject
    private UserMapper userMapper;

    @Transactional
    public User doSomeBusinessStuff(String userId) {
      return this.userMapper.getUser(userId);
    }

  }
}
