package com.alexshabanov.sample.eol.migration;

import com.alexshabanov.sample.eol.migration.service.MigrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class App implements Runnable {
  private final Logger log;
  private final MigrationService.Contract migrationService;

  public App(MigrationService.Contract migrationService) {
    this.log = LoggerFactory.getLogger(App.class);
    this.migrationService = migrationService;
  }

  public static void main(String[] args) throws Exception {
    final String[] contextPaths = {"classpath:/eolMigrationApp/spring/app-context.xml"};

    try (final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(contextPaths, false)) {
      addEnvironmentProperties(context);
      context.refresh(); // explicitly refresh the context
      context.start();

      // get Runnable application bean and run it
      context.getBean("app", Runnable.class).run();
    }
  }

  @Override
  public void run() {
    log.info("App is running");
    migrationService.runMigration();
  }

  //
  // Private
  //

  private static void addEnvironmentProperties(ConfigurableApplicationContext context) throws IOException {
    final List<Resource> resources = new ArrayList<>(2);
    resources.add(context.getResource("classpath:/eolMigrationApp/core.properties"));

    // do we have property override? if yes - add it
    final String propsOverridePath = System.getProperty("app.properties.override");
    if (propsOverridePath != null) {
      resources.add(context.getResource(propsOverridePath));
    }

    // fill properties
    final Properties properties = new Properties();
    for (final Resource resource : resources) {
      if (!resource.exists()) {
        throw new IOException("Resource " + resource.getFilename() + " does not exist");
      }

      PropertiesLoaderUtils.fillProperties(properties, resource);
    }

    // create property source and insert it into a context
    final PropertiesPropertySource propertySource = new PropertiesPropertySource("eolApp", properties);
    context.getEnvironment().getPropertySources().addFirst(propertySource);
  }
}
