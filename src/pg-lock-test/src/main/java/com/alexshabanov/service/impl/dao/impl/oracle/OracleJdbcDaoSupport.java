package com.alexshabanov.service.impl.dao.impl.oracle;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Adds Oracle-specific support for JDBC DAO.
 */
public abstract class OracleJdbcDaoSupport extends JdbcDaoSupport {
    /**
     * Estimated size of the queries constructed in {@see #queryWithRange}.
     */
    private static final int ESTIMATED_QUERY_SIZE = 256;

    /**
     * Executes query for statements with limit/offset using PL/SQL specifics.
     *
     * @param selectParameters  Comma-separated parameters of the SELECT statement, trailing whitespaces is not needed.
     * @param sqlFromClause     SQL clause starting from the "FROM" keyword in the SELECT statement.
     * @param rowMapper         Extractor that creates the particular element from the result set.
     * @param offset            Offset in the resultant set returned by the given SQL query.
     * @param limit             Maximum size of the elements in the returned list,
     *                          if zero, empty list will be returned without querying database.
     * @param parameters        Query parameters, except for limit/offset parameters.
     * @param <T>               List element type.
     * @return Non-null list.
     * @throws DataAccessException On error.
     */
    protected <T> List<T> queryWithRange(String selectParameters, String sqlFromClause,
                                         RowMapper<T> rowMapper,
                                         int offset, int limit,
                                         Object ... parameters) throws DataAccessException {
        if (limit == 0) {
            return Collections.emptyList();
        }

        // Query parameters + 2 parameters: offset and limit
        final Object[] allQueryParameters = new Object[parameters.length + 2];
        System.arraycopy(parameters, 0, allQueryParameters, 0, parameters.length);
        // Last two parameters: offset/limit
        allQueryParameters[parameters.length] = offset + 1;
        allQueryParameters[parameters.length + 1] = offset + limit;

        // Construct SQL query.
        final String sqlStatement = new StringBuilder(ESTIMATED_QUERY_SIZE)
                .append("SELECT ").append(selectParameters).append(" FROM (\n")
                .append("   SELECT ").append(selectParameters).append(", rownum AS inner_rownum FROM (\n")
                .append("       SELECT ").append(selectParameters).append(" ").append(sqlFromClause).append("\n")
                .append("   )\n")
                .append(") WHERE inner_rownum BETWEEN ? AND ?")
                .toString();

        return getJdbcTemplate().query(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        return con.prepareStatement(sqlStatement);
                    }
                },
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        for (int i = 0; i < allQueryParameters.length; ++i) {
                            ps.setObject(i + 1, allQueryParameters[i]);
                        }
                    }
                }, new RowMapperResultSetExtractor<T>(rowMapper));
    }
}
