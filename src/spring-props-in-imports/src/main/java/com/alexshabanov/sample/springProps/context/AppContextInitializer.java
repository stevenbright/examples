package com.alexshabanov.sample.springProps.context;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * A helper, that does injection of the beans to the application context.
 */
public final class AppContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    // use application context to get the resource
    final Resource coreProperties = applicationContext.getResource("classpath:/springPropsApp/core.properties");
    if (!coreProperties.exists()) {
      throw new IllegalStateException("core.properties file does not exist");
    }

    final String contextOverridePath = System.getProperty("app.contextOverride");
    final Resource overrideProperties = applicationContext.getResource(contextOverridePath);
    if (!overrideProperties.exists()) {
      throw new IllegalStateException("Override properties file does not exist at " + contextOverridePath);
    }

    try {
      // fill properties
      final Properties properties = PropertiesLoaderUtils.loadProperties(coreProperties);
      PropertiesLoaderUtils.fillProperties(properties, overrideProperties);

      // initialize property source based on these properties
      final PropertiesPropertySource propertySource = new PropertiesPropertySource("profile", properties);

      // actually insert property source
      applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
    } catch (IOException e) {
      throw new BeanInitializationException("Error while initializing context with properties", e);
    }
  }
}
