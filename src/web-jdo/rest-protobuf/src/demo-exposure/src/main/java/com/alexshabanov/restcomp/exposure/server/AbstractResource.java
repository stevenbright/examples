package com.alexshabanov.restcomp.exposure.server;

import com.alexshabanov.restcomp.exposure.server.exception.ServiceNotFoundException;
import com.alexshabanov.restcomp.exposure.server.service.ExposedService;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Providers;

/**
 * Base resource implementation.
 */
public abstract class AbstractResource {
    @Context
    public Providers providers;

    /**
     * Gets certain exposed service from the context providers.
     * @param serviceClass Exposed service descendant class.
     * @param <T> Exposed service descendant class.
     * @return Instance of the exposed service.
     * @throws ServiceNotFoundException If service is not available.
     */
    protected <T extends ExposedService> T getService(Class<T> serviceClass) throws ServiceNotFoundException {
        final ContextResolver<ExposedService> contextResolver = providers.getContextResolver(ExposedService.class, null);
        if (contextResolver == null) {
            throw new ServiceNotFoundException("ExposedService context resolver is missing");
        }

        final ExposedService service = contextResolver.getContext(serviceClass);
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
