package com.alexshabanov.guiceresteasydemo.config;

import com.alexshabanov.guiceresteasydemo.ftl.FreemarkerProvider;
import com.alexshabanov.guiceresteasydemo.resource.HelloResource;
import com.alexshabanov.guiceresteasydemo.resource.PublicPageResource;
import com.alexshabanov.guiceresteasydemo.resource.UserResource;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.truward.protobuf.jaxrs.ProtobufJacksonProvider;
import com.truward.protobuf.jaxrs.ProtobufProvider;
import freemarker.template.Configuration;

import java.nio.charset.StandardCharsets;

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
    binder.bind(PublicPageResource.class);

    // mappers
    // protobuf provider (reader/writer)
    binder.bind(ProtobufProvider.class).toInstance(new ProtobufProvider());

    // optional (may be nice to have for web/nodejs apps)
    binder.bind(ProtobufJacksonProvider.class).toInstance(new ProtobufJacksonProvider());

    // MVC mapper
    final Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_23);
    freemarkerConfiguration.setClassLoaderForTemplateLoading(Thread.currentThread().getContextClassLoader(), "web/ftl");
    freemarkerConfiguration.setDefaultEncoding(StandardCharsets.UTF_8.name());
    binder.bind(FreemarkerProvider.class).toInstance(new FreemarkerProvider(freemarkerConfiguration));
  }
}
