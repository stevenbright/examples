package net.threadtxtest.service.internal.dao.impl.oracle;

import net.threadtxtest.service.internal.dao.impl.generic.BankOperationDaoGenericImpl;
import org.springframework.dao.DataAccessException;

/**
 * Oracle-specific implementation of the bank operation DAO.
 */
public final class BankOperationDaoOracleImpl extends BankOperationDaoGenericImpl {
    /**
     * {@inheritDoc}
     */
    @Override
    protected long getIdentityId() throws DataAccessException {
        return getJdbcTemplate().queryForLong("select SEQ_BANK_OPERATION_ID.currval from dual");
    }
}
