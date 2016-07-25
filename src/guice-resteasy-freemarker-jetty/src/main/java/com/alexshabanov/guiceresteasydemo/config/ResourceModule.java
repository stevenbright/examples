package com.alexshabanov.guiceresteasydemo.config;

import com.alexshabanov.guiceresteasydemo.resource.HelloResource;
import com.alexshabanov.guiceresteasydemo.resource.UserResource;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.truward.protobuf.jaxrs.ProtobufJacksonProvider;
import com.truward.protobuf.jaxrs.ProtobufProvider;

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
    // protobuf provider (reader/writer)
    binder.bind(ProtobufProvider.class).toInstance(new ProtobufProvider());

    // optional (may be nice to have for web/nodejs apps)
    binder.bind(ProtobufJacksonProvider.class).toInstance(new ProtobufJacksonProvider());
  }
}
