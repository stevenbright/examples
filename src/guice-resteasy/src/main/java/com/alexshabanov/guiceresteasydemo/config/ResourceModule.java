package com.alexshabanov.guiceresteasydemo.config;

import com.alexshabanov.guiceresteasydemo.launcher.JaxrsAssetInspector;
import com.alexshabanov.guiceresteasydemo.resource.HelloResource;
import com.alexshabanov.guiceresteasydemo.resource.UserResource;
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
    final Class<?>[] resourceClasses = {
        HelloResource.class,
        UserResource.class
    };

    for (final Class<?> resourceClass : resourceClasses) {
      binder.bind(resourceClass);
    }

    // bind jackson converters for JAXB/JSON serialization
//    binder.bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
//    binder.bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);

    binder.bind(JaxrsAssetInspector.class).toInstance(JaxrsAssetInspector.of(resourceClasses));
  }

}
