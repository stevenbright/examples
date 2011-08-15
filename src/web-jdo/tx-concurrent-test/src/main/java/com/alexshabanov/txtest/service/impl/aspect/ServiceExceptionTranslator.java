package com.alexshabanov.txtest.service.impl.aspect;

import com.alexshabanov.txtest.service.exception.ServiceDaoException;
import com.alexshabanov.txtest.service.exception.ServiceException;
import com.alexshabanov.txtest.service.exception.UncategorizedServiceException;
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

