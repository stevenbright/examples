package net.threadtxtest.service.internal.dao.impl.hsqldb;

import net.threadtxtest.service.internal.dao.impl.generic.UserDaoGenericImpl;
import org.springframework.dao.DataAccessException;

/**
 * HSQLDB-specific implementation of UserDao.
 */
public class UserDaoHsqldbImpl extends UserDaoGenericImpl {
    /**
     * {@inheritDoc}
     */
    @Override
    protected long getIdentityId() throws DataAccessException {
        return getJdbcTemplate().queryForLong("call identity()");
    }
}
