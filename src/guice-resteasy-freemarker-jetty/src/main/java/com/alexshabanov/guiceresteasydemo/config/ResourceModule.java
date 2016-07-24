package com.alexshabanov.guiceresteasydemo.config;

import com.alexshabanov.guiceresteasydemo.resource.HelloResource;
import com.alexshabanov.guiceresteasydemo.resource.UserResource;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Resource module - enumeration of all the JAX RS resources.
 *
 * @author Alexander Shabanov
 */
public final class ResourceModule implements Module {

  @Override
  public void configure(Binder binder) {
    // resources
    binder.bind(HelloResource.class);
    binder.bind(UserResource.class);

    // mappers
    final ObjectMapper mapper = new ObjectMapper(); // TODO: extra mapper settings
    mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
    // json provider (reader/writer)
    final JacksonJsonProvider jsonProvider = new JacksonJsonProvider(mapper);
    // bind jackson converters for JAXB/JSON serialization
    binder.bind(JacksonJsonProvider.class).toInstance(jsonProvider);
  }
}
