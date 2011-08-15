package com.alexshabanov.txtest.service.impl.dao.impl;

import com.alexshabanov.txtest.service.impl.dao.BankDao;
import com.alexshabanov.txtest.service.model.BalanceChange;
import com.alexshabanov.txtest.service.model.BalanceChangeEntry;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Implements bank DAO.
 */
public final class BankDaoImpl extends JdbcDaoSupport implements BankDao {

    /**
     * {@inheritDoc}
     */
    public long addUser(String name) {
        getJdbcTemplate().update(
                "insert into USER_ACCOUNT (USER_NAME, BALANCE, CREATED) " +
                        "values (?, 0.0, now())",
                name);
        return getJdbcTemplate().queryForLong("call identity()");
    }

    /**
     * {@inheritDoc}
     */
    public double getBalance(final long userId) {
        return getJdbcTemplate().query(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement("select BALANCE from USER_ACCOUNT where ID=? for update");
            }
        }, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, userId);
            }
        }, new ResultSetExtractor<Double>() {
            public Double extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return rs.getDouble(1);
                }

                throw new DataIntegrityViolationException("No balance returned");
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    public void setBalance(long userId, double balance) {
        getJdbcTemplate().update("update USER_ACCOUNT set BALANCE=? where ID=?", balance, userId);
    }

    /**
     * {@inheritDoc}
     */
    public void addToBalance(long userId, double amount) {
        getJdbcTemplate().update("update USER_ACCOUNT set BALANCE=BALANCE+? where ID=?", amount, userId);
    }

    /**
     * {@inheritDoc}
     */
    public long addBalanceChange(long userId, double amount, BalanceChange balanceChange) {
        getJdbcTemplate().update(
                "insert into BALANCE_CHANGE (USER_ID, AMOUNT, CODE, CREATED) " +
                        "values (?, ?, ?, now())",
                userId, amount, balanceChange.getValue());
        return getJdbcTemplate().queryForLong("call identity()");
    }

    /**
     * {@inheritDoc}
     */
    public Collection<BalanceChangeEntry> getBalanceChanges(long userId) {
        return getJdbcTemplate().query("select ID, USER_ID, AMOUNT, CODE from BALANCE_CHANGE " +
                "where USER_ID=? order by CREATED desc",
                new RowMapper<BalanceChangeEntry>() {
                    public BalanceChangeEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
                        final BalanceChangeEntry result = new BalanceChangeEntry();
                        result.setId(rs.getLong(1));
                        result.setUserId(rs.getLong(2));
                        result.setAmount(rs.getDouble(3));
                        result.setBalanceChange(BalanceChange.getEnumByValue(rs.getInt(4)));
                        return result;
                    }
                }, userId);
    }
}
