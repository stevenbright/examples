package net.threadtxtest.service.internal.dao.impl.hsqldb;

import net.threadtxtest.service.internal.dao.impl.generic.BankOperationDaoGenericImpl;
import org.springframework.dao.DataAccessException;

/**
 * HSQLDB-specific implementation of the BankOperationDao.
 */
public final class BankOperationDaoHsqldbImpl extends BankOperationDaoGenericImpl {
    /**
     * {@inheritDoc}
     */
    @Override
    protected long getIdentityId() throws DataAccessException {
        return getJdbcTemplate().queryForLong("call identity()");
    }
}
