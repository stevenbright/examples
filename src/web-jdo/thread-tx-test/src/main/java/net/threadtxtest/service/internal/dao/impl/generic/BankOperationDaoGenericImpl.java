package net.threadtxtest.service.internal.dao.impl.generic;

import net.threadtxtest.service.BankOperationStatus;
import net.threadtxtest.service.internal.dao.BankOperationDao;
import net.threadtxtest.service.internal.domain.BankOperation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

/**
 * Common JDBC implementation of the BankOperationDao.
 */
public abstract class BankOperationDaoGenericImpl extends JdbcDaoSupport implements BankOperationDao {

    /**
     * Gets unique ID of the inserted record into BANK_OPERATION table.
     * @return Identity ID
     * @throws DataAccessException On error
     */
    protected abstract long getIdentityId() throws DataAccessException;

    /**
     * {@inheritDoc}
     */
    public long addOperation(long userId, BigDecimal amount) throws DataAccessException {
        getJdbcTemplate().update("insert into BANK_OPERATION (USER_ID, AMOUNT, STATUS, CREATED)\n" +
                "values (?, ?, ?, ?)", userId, amount, BankOperationStatus.PENDING, new Date());

        return getIdentityId();
    }

    /**
     * {@inheritDoc}
     */
    public BankOperation getOperation(final long operationId) throws DataAccessException {
        return getJdbcTemplate().query(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        return con.prepareStatement("select ID, USER_ID, AMOUNT, STATUS from BANK_OPERATION where ID=?");
                    }
                },
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setLong(1, operationId);
                    }
                },
                new ResultSetExtractor<BankOperation>() {
                    public BankOperation extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if (rs.next()) {
                            final BankOperation result = new BankOperation();
                            result.setId(rs.getLong(1));
                            result.setUserId(rs.getLong(2));
                            result.setAmount(rs.getBigDecimal(3));
                            result.setStatus(BankOperationStatus.getEnumByValue(rs.getInt(4)));
                            return result;
                        }

                        // no such user
                        return null;
                    }
                });
    }

    /**
     * {@inheritDoc}
     */
    public void updateOperationStatus(long operationId, BankOperationStatus status) throws DataAccessException {
        getJdbcTemplate().update("update BANK_OPERATION set STATUS=? where operationId=?", status.getValue(), operationId);
    }

    /**
     * {@inheritDoc}
     */
    public Collection<Long> getPendingOperations() throws DataAccessException {
        return getJdbcTemplate().query("select ID from BANK_OPERATION where STATUS=?", new RowMapper<Long>() {
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong(1);
            }
        }, BankOperationStatus.PENDING);
    }
}
