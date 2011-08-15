package org.microblog.exposure.server;

import org.microblog.exposure.server.exception.ServiceNotFoundException;
import org.microblog.exposure.server.service.BaseService;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Providers;

/**
 * Base class for all the resources.
 */
public abstract class AbstractResource {
    @Context
    public Providers providers;

    /**
     * Gets certain exposed service from the context providers.
     * @param serviceClass Exposed service descendant class.
     * @param <T> Exposed service descendant class.
     * @return Instance of the exposed service.
     * @throws org.microblog.exposure.server.exception.ServiceNotFoundException If service is not available.
     */
    protected <T extends BaseService> T getService(Class<T> serviceClass) throws ServiceNotFoundException {
        final ContextResolver<BaseService> contextResolver = providers.getContextResolver(BaseService.class, null);
        if (contextResolver == null) {
            throw new ServiceNotFoundException("Service context resolver is missing");
        }

        final BaseService service = contextResolver.getContext(serviceClass);
        if (service == null) {
            throw new ServiceNotFoundException("Context resolver does not provide " + serviceClass);
        }

        if (!serviceClass.isAssignableFrom(service.getClass())) {
            throw new ServiceNotFoundException("Unconvertable instance of the " + serviceClass + " is given");
        }

        @SuppressWarnings("unchecked")
        final T result = (T) service;
        return result;
    }
}
