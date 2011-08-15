package com.truward.gbp.server.processors;

import com.truward.gbp.server.DummyDao;
import com.truward.gbp.shared.service.ServiceException;
import com.truward.gbp.shared.service.ServiceRequest;
import com.truward.gbp.shared.service.ServiceResponse;

/**
 * Base interface for all the request processors
 */
public interface Processor<T extends ServiceResponse> {
    interface Context {
        DummyDao getDao();

        String getCurrentUser();

        void setCurrentUser(String username);
    }

    T processRequest(ServiceRequest<T> action, Context context) throws ServiceException;
}
