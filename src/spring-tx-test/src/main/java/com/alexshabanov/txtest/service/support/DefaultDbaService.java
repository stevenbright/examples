package com.alexshabanov.txtest.service.support;

import com.alexshabanov.txtest.service.DbaService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Service
@Transactional
public final class DefaultDbaService implements DbaService {

    @Inject
    private JdbcOperations jdbcOperations;

    @Inject
    private DataSource dataSource;


    @Override
    public void doDbAccess() {
        try {
            {
                final Connection con = dataSource.getConnection();
                final Statement st = con.createStatement();
                int i = st.executeUpdate("INSERT INTO person (id, name, age) VALUES (8, 'tanya', 18);");
                System.out.println("Update succeed, row(s) inserted: " + i);
                con.close();
            }

            {
                final Connection con = dataSource.getConnection();
                final Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery("SELECT * FROM person");

                int count = 0;
                final ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
                while (rs.next()) {
                    final Map<String, Object> m = rowMapper.mapRow(rs, count);
                    System.out.println("m = " + m);

                    ++count;
                }
                System.out.println("Query succeeded, row(s) fetched: " + count);

                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
