package com.alexshabanov.restcomp.exposure.mockprovider;

import com.alexshabanov.restcomp.exposure.server.service.DemoService;
import com.alexshabanov.restcomp.exposure.server.service.ExposedService;
import org.easymock.EasyMock;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Represents context resolver for the exposed services.
 */
@Provider
public class MockServiceContextResolver implements ContextResolver<ExposedService> {

    public static final DemoService DEMO_SERVICE = EasyMock.createMock(DemoService.class);

    /**
     * {@inheritDoc}
     */
    public ExposedService getContext(Class<?> type) {
        if (DemoService.class.equals(type)) {
            return DEMO_SERVICE;
        }

        return null;
    }
}
