package net.threadtxtest.service.internal.dao.impl.oracle;

import net.threadtxtest.service.internal.dao.impl.generic.UserDaoGenericImpl;
import org.springframework.dao.DataAccessException;

/**
 * Oracle-specific implementation of the user DAO.
 */
public final class UserDaoOracleImpl extends UserDaoGenericImpl {
    /**
     * {@inheritDoc}
     */
    @Override
    protected long getIdentityId() throws DataAccessException {
        return getJdbcTemplate().queryForLong("select SEQ_USER_ACCOUNT_ID.currval from dual");
    }
}
