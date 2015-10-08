package com.alexshabanov.gjmdemo.server.config;

import com.alexshabanov.gjmdemo.logic.config.LogicModule;
import com.alexshabanov.gjmdemo.server.resource.SampleResource;
import com.alexshabanov.gjmdemo.server.resource.UserResource;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Example Guice Server configuration. Creates an Injector, and binds it to
 * whatever Modules we want. In this case, we use an anonymous Module, but other
 * modules are welcome as well.
 */
public final class ServerConfig extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new ServletModule() {
      @Override
      protected void configureServlets() {
        install(new LogicModule());

        bind(SampleResource.class);
        bind(UserResource.class);


        // bind jackson converters for JAXB/JSON serialization
        bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
        bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);

        final Map<String, String> containerProps = new HashMap<>();
        if (Boolean.valueOf(System.getProperty("enableJettyTrace"))) {
          containerProps.put("com.sun.jersey.config.feature.Trace", "true");
        }

        serve("*").with(GuiceContainer.class, containerProps);
      }
    });
  }
}