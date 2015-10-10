package com.alexshabanov.gjmdemo.logic.service.helper;

import com.google.inject.name.Named;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public final class DbInitializer {
  private final Logger log = LoggerFactory.getLogger(getClass());
  @Inject private SqlSessionFactory sqlSessionFactory;
  @Inject @Named("gjmDemo.dao.initScripts") private String initScripts;
  @Inject @Named("gjmDemo.dao.diagScripts") private String diagScripts;

  public void initialize() {
    final Environment environment = sqlSessionFactory.getConfiguration().getEnvironment();
    final DataSource dataSource = environment.getDataSource();

    try (Connection connection = dataSource.getConnection()) {
      final ScriptRunner runner = new ScriptRunner(connection);
      runner.setLogWriter(null);
      runner.setErrorLogWriter(null);
      runner.setAutoCommit(true);
      runner.setStopOnError(true);

      try {
        executeScripts(runner, Arrays.asList(diagScripts.split(",")));
        return;
      } catch (RuntimeSqlException ignored) {
        // do nothing, schema is non-existent or broken
      }

      executeScripts(runner, Arrays.asList(initScripts.split(",")));
    } catch (SQLException | IOException e) {
      throw new IllegalStateException("Can't initialize DB", e);
    }
  }

  //
  // Private
  //

  private void executeScripts(ScriptRunner runner, List<String> scripts) throws SQLException, IOException {
    for (final String script : scripts) {
      if (script.isEmpty()) {
        continue;
      }
      log.info("Executing init script={}", script);
      runner.runScript(Resources.getResourceAsReader(script));
    }
  }
}
