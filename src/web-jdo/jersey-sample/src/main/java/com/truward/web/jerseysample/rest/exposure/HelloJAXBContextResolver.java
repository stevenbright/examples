package com.truward.web.jerseysample.rest.exposure;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.truward.web.jerseysample.rest.model.Greeting;
import com.truward.web.jerseysample.rest.model.UserProfile;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Creates JAXB context resolver for particular classes.
 * TODO: remove, looks like it is never used and the user still has to use @XmlRootElement
 */
@Provider
public class HelloJAXBContextResolver implements ContextResolver<JAXBContext> {
    private static Class<?>[] SUPPORTED_CLASSES = new Class<?>[] {
            UserProfile.class,
            Greeting.class, // TODO: remove it???
    };

    private JAXBContext context;

    /**
     * {@inheritDoc}
     */
    public JAXBContext getContext(Class<?> type) {
        boolean supported = false;
        for (final Class<?> supportedClass : SUPPORTED_CLASSES) {
            if (type.equals(supportedClass)) {
                supported = true;
                break;
            }
        }

        // do not provide any context for unsupported classes
        if (!supported) {
            return null;
        }

        if (context == null) {
            try {
                //new JSONJAXBContext(JSONConfiguration.natural().build(), SUPPORTED_CLASSES);
                //context = JAXBContext.newInstance(SUPPORTED_CLASSES);
                context = new JSONJAXBContext(JSONConfiguration.natural().build(), SUPPORTED_CLASSES);

                /*
                    You can exclude all the listed dependencies in jersey-json if you use
                    context = JAXBContext.newInstance(SUPPORTED_CLASSES);
                */
            } catch (JAXBException e) {
                throw new UnsupportedOperationException("Can't create JAXB context for supported classes", e);
            }
        }

        return context;
    }
}
