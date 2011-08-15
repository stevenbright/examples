package net.threadtxtest.service.internal.dao.impl.generic;

import net.threadtxtest.service.internal.dao.UserDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Generic JDBC implementation of the user's DAO.
 */
public abstract class UserDaoGenericImpl extends JdbcDaoSupport implements UserDao {

    /**
     * Gets unique ID of the inserted record into USER_ACCOUNT table.
     * @return Identity ID
     * @throws DataAccessException On error
     */
    protected abstract long getIdentityId() throws DataAccessException;

    /**
     * {@inheritDoc}
     */
    public long addUser(String name) throws DataAccessException {
        getJdbcTemplate().update("insert into USER_ACCOUNT\n" +
                "   (USER_NAME, BALANCE, CREATED)\n" +
                "   values (?, 0, ?)",
                name,
                new Date());

        return getIdentityId();
    }

    /**
     * {@inheritDoc}
     */
    public Long findUserByName(final String name) throws DataAccessException {
        return getJdbcTemplate().query(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        return con.prepareStatement("select ID from USER_ACCOUNT where USER_NAME=?");
                    }
                },
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, name);
                    }
                },
                new ResultSetExtractor<Long>() {
                    public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if (rs.next()) {
                            return rs.getLong(1);
                        }

                        // no such user
                        return null;
                    }
                });
    }

    /**
     * {@inheritDoc}
     */
    public BigDecimal getBalance(long userId) throws DataAccessException {
        return getJdbcTemplate().queryForObject("select BALANCE from USER_ACCOUNT where ID=?", BigDecimal.class, userId);
    }

    /**
     * {@inheritDoc}
     */
    public void modifyBalance(long userId, BigDecimal amount) throws DataAccessException {
        getJdbcTemplate().update("update USER_ACCOUNT set BALANCE=BALANCE+? where ID=?", amount, userId);
    }
}
