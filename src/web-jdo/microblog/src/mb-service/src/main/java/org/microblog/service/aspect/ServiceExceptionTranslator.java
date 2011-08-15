package org.microblog.service.aspect;

import org.microblog.exposure.server.exception.ServiceException;
import org.microblog.service.exception.ServiceDaoException;
import org.microblog.service.exception.UncategorizedServiceException;
import org.springframework.dao.DataAccessException;

/**
 * Represents service exception translator wrap-up.
 */
public final class ServiceExceptionTranslator {
    public void afterThrowing(Exception e) throws ServiceException {
        if (e instanceof ServiceException) {
            // do nothing
            return;
        }

        if (e instanceof DataAccessException) {
            throw new ServiceDaoException(e);
        }

        throw new UncategorizedServiceException(e);
    }
}
