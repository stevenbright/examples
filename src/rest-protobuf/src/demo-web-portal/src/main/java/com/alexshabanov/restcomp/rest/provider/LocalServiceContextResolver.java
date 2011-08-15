package com.alexshabanov.restcomp.rest.provider;

import com.alexshabanov.restcomp.exposure.server.exception.ServiceException;
import com.alexshabanov.restcomp.exposure.server.service.DemoService;
import com.alexshabanov.restcomp.exposure.server.service.ExposedService;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.Collection;

/**
 * Local service context resolver.
 */
@Provider
public class LocalServiceContextResolver implements ContextResolver<ExposedService> {

    private int sum = 0;

    public final DemoService DEMO_SERVICE = new DemoService() {
        public Collection<Integer> getFavoriteNumbers() throws ServiceException {
            return Arrays.asList(1, 2 ,3);
        }

        public void addNumber(int number) {
            sum += number;
        }

        public int getSum() {
            return sum;
        }
    };

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
