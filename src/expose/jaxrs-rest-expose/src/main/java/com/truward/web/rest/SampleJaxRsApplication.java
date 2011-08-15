package com.truward.web.rest;

import com.truward.web.rest.services.ComplexService;
import com.truward.web.rest.services.SimpleService;
import org.restlet.Component;
import org.restlet.ext.jaxrs.JaxRsApplication;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


public class SampleJaxRsApplication extends JaxRsApplication {
    public SampleJaxRsApplication(Component component) {
        super(component.getContext().createChildContext());

        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(SimpleService.class);
        classes.add(ComplexService.class);

        add(new Application() {
            @Override
            public Set<Class<?>> getClasses() { return classes; }
        });

        component.getDefaultHost().attach(this);
    }
}
